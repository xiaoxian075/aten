package com.aten.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aten.client.JiguangClient;
import com.aten.client.SmsClient;
import com.aten.dao.AccountBillDao;
import com.aten.dao.AccountDao;
import com.aten.dao.BillFlowDao;
import com.aten.dao.GoodsDao;
import com.aten.dao.ManaFundsDao;
import com.aten.dao.OrderDao;
import com.aten.dao.OrderRefundDao;
import com.aten.function.FuncAssem;
import com.aten.function.SysconfigFuc;
import com.aten.model.bean.ExpressInfo;
import com.aten.model.bean.JPushBean;
import com.aten.model.bean.OrderPushNode;
import com.aten.model.bean.PageBean;
import com.aten.model.bean.PageListBean;
import com.aten.model.orm.Account;
import com.aten.model.orm.AccountBill;
import com.aten.model.orm.BillFlow;
import com.aten.model.orm.Goods;
import com.aten.model.orm.ManaFunds;
import com.aten.model.orm.OrderAuxiliary;
import com.aten.model.orm.OrderDeposit;
import com.aten.model.orm.OrderDetailLvo;
import com.aten.model.orm.OrderDetailNode;
import com.aten.model.orm.OrderExpressNode;
import com.aten.model.orm.OrderFullbook;
import com.aten.model.orm.OrderNode;
import com.aten.model.orm.OrderRefundLogsNode;
import com.aten.model.orm.OrderRefundNode;
import com.aten.model.orm.OrderTransNode;
import com.aten.model.vo.OrderCPVo;
import com.aten.model.vo.OrderDetailCPVo;
import com.aten.model.vo.OrderRefundVo;
import com.aten.model.vo.OrderVo;
import com.aten.model.vo.RefundVo;
import com.aten.service.OrderService;
import com.communal.node.ReqMsg;
import com.communal.thirdservice.logistics.Logistics;
import com.communal.thirdservice.logistics.LogisticsChildNode;
import com.communal.thirdservice.logistics.LogisticsNode;
import com.communal.util.JsonUtil;
import com.communal.util.RandomCharUtil;
import com.google.gson.reflect.TypeToken;

@Service("orderService")
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private OrderRefundDao refundDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired 
	private ManaFundsDao manaFundsDao;
	
	@Autowired
	private BillFlowDao billFlowDao;
	
	@Autowired
	private AccountBillDao accountBillDao;
	
	@Autowired
	private GoodsDao goodsDao;


	@Override
	public PageBean<OrderVo> selectlistOrderByPage(int dpage,int dcount,Map<String, Object> paraMap) {
		List<OrderVo> orderLvo = new ArrayList<OrderVo>();
		
		int allcount = orderDao.selectcountOrderByPage(paraMap);
		dpage = (dpage-1)*dcount;
		paraMap.put("page", dpage);
		paraMap.put("count", dcount);
		List<OrderNode> listOrder = orderDao.selectlistOrderByPage(paraMap);

		for (OrderNode node : listOrder) {
			BigInteger order_id = node.getOrder_id();
			String order_number = node.getOrder_number();
			List<OrderDetailLvo> listDetailLvo = new ArrayList<OrderDetailLvo>();
			
			List<OrderDetailNode> detail = orderDao.selectlistOrderDetailByOrderid(order_id);
			if (detail==null) {
				continue;
			}
			for (OrderDetailNode dnode : detail) {
				listDetailLvo.add(new OrderDetailLvo(dnode));
			}
			
			OrderFullbook fullbook = null;	//全额销售
			if (node.getOrder_type()==2) {
				fullbook = orderDao.selectoneOrderFullbookByOrderid(order_id);
				if (fullbook==null) {
					continue;
				}
			}
			
			OrderDeposit depositbook = null;//预付销售
			if (node.getOrder_type()==3) {
				depositbook = orderDao.selectoneOrderDepositbookByOrderid(order_id);
				if (depositbook==null) {
					continue;
				}
			}
			
			OrderExpressNode express = orderDao.selectonOrderExpressByOrderid(order_number);
			if (express==null) {
				continue;
			}
			
			orderLvo.add(new OrderVo(node,detail,fullbook,depositbook,express,null));
		}
		
		PageListBean page = FuncAssem.caclPage(dpage,dcount,allcount);
		return new PageBean<OrderVo>(page,orderLvo);
	}
	

	@Override
	public ReqMsg<OrderVo> selectOrderById(BigInteger order_id) {
		
		OrderNode order = orderDao.selectoneOrderByOrderid(order_id);
		if (order==null) {
			return new ReqMsg<OrderVo>(101,"异常",null);
		}
		
		List<OrderDetailNode> detail = orderDao.selectlistOrderDetailByOrderid(order_id);
		if (detail==null || detail.size()==0) {
			return new ReqMsg<OrderVo>(101,"异常",null);
		}

		OrderFullbook fullbook = null;	//全额销售
		if (order.getOrder_type()==2) {
			fullbook = orderDao.selectoneOrderFullbookByOrderid(order_id);
			if (fullbook==null) {
				return new ReqMsg<OrderVo>(101,"异常",null);
			}
		}
		
		OrderDeposit depositbook = null;//预付销售
		if (order.getOrder_type()==3) {
			depositbook = orderDao.selectoneOrderDepositbookByOrderid(order_id);
			if (depositbook==null) {
				return new ReqMsg<OrderVo>(101,"异常",null);
			}
		}
		
		OrderExpressNode express = orderDao.selectonOrderExpressByOrderid(order.getOrder_number());
		if (express==null) {
			return new ReqMsg<OrderVo>(101,"异常",null);
		}
		
		OrderAuxiliary auxiliary = orderDao.selectoneOrderAuxiliaryByOrderid(order.getOrder_number());	//发票信息可以为null

		return new ReqMsg<OrderVo>(0,"succ",new OrderVo(order,detail,fullbook,depositbook,express,auxiliary));
	}

	@Override
	@Transactional
	public ReqMsg<Object> sendGoods(BigInteger order_id, String fast_code, String fast_waybill, String opr) {
		OrderNode order = orderDao.selectoneOrderByOrderid(order_id);
		if (order==null) {
			return new ReqMsg<Object>(101,"无效订单",null);
		}
		
		if (order.getOrder_state()!=1) {	//状态为  等待卖家发货(买家已付款) 才可以发货
			return new ReqMsg<Object>(102,"订单状态不可发货",null);
		}
		
		
		List<OrderDetailNode> listDetail = orderDao.selectlistOrderDetailByOrderid(order.getOrder_id());
		if (listDetail==null || listDetail.size()==0) {
			return new ReqMsg<Object>(101,"无效订单",null);
		}
		for (OrderDetailNode node : listDetail) {
			//状态   0：未付款（等待买家付款） 1：等待卖家发货(买家已付款) 2：等待买家确认收货（卖家已发货） 3：交易成功 4： 退款中 5： 退款成功（交易关闭） 6： 未付款前，关闭交易 7: 退货退款中 8: 退货退款成功 9:售后中 10: 售后成功
			if (node.getState()==4) {	//存在退款中的商品，不让发货 
				return new ReqMsg<Object>(103,"您有存在退款中的商品，不能发货",null);
			}
		}

		OrderExpressNode orderExpress = orderDao.selectonOrderExpressByOrderid(order.getOrder_number());
		if (orderExpress==null) {
			return new ReqMsg<Object>(101,"无效订单",null);
		}
		
		long curTime = new Date().getTime();
		long time_out_time = curTime + 24*60*60*1000*Integer.valueOf(SysconfigFuc.getSysValue("cfg_order_confirmGoods"));	//配置中以天为单位
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		Timestamp curStamp = new Timestamp(curTime-6000000);	//减一天存进去
		paraMap.put("oe_id", orderExpress.getOe_id());
		paraMap.put("fast_code", fast_code);
		paraMap.put("fast_waybill", fast_waybill);
		paraMap.put("logistics_state", 1);
		paraMap.put("last_update_time", curStamp);
		if (1!=orderDao.updateoneOrderExpressForExpressSendgoods(paraMap)) {
			throw new RuntimeException();
		}
		
		paraMap.clear();
		paraMap.put("order_id", order_id);
		paraMap.put("is_send", 1);			//将发货状态置为发货
		paraMap.put("order_state", 2);		//将状态置为  等待买家确认收货（卖家已发货）
		paraMap.put("time_out_time", new Timestamp(time_out_time));
		paraMap.put("send_time", new Timestamp(curTime));
		if (1!=orderDao.updateoneOrderForOrderstate(paraMap)) {	
			throw new RuntimeException();
		}
		
		paraMap.clear();
		paraMap.put("order_id", order_id);
		paraMap.put("state", 1);	//待发货的商品
		paraMap.put("newState", 2);	//置为待收货
		paraMap.put("delivery_state", 1);
		paraMap.put("fast_code", fast_code);
		paraMap.put("fast_waybill", fast_waybill);
		paraMap.put("send_time", new Timestamp(new Date().getTime()));
		if (0>=orderDao.updateoneOrderDetailForSendgoods(paraMap)) {
			throw new RuntimeException();
		}
		
		String account_name = order.getBuyer_nick();
		if (account_name==null) 
			account_name = "-";
		String com_name = order.getSeller_nick();
		if (com_name==null) 
			com_name = "-";
		OrderTransNode orderTrans = new OrderTransNode(
				BigInteger.ZERO,
				order.getOrder_id(),
				order.getAccount_id(),
				account_name,
				order.getSeller_id(),
				com_name,
				"卖家发货",
				"卖家发货",
				new Timestamp(new Date().getTime()));
		orderDao.insertoneOrderTrans(orderTrans);
		
		String content = SysconfigFuc.getSysValue("cfg_send_goods_push");	//你购买的商品商家已发货，请注意查收。>>
		
		//短信通知客户
		Account account = accountDao.getById(order.getAccount_id());
		if (account!=null) {
			SmsClient.send(account.getMobile(), content);
		}
		//系统推送
		if (account.getPush_code()!=null) {
			JiguangClient.send(new JPushBean(account.getPush_code(),content,content,order.getOrder_number(),"1",String.valueOf(account.getId())));
		}

		
		return new ReqMsg<Object>(0,"发货成功",null);
	}

	@Override
	@Transactional
	public ReqMsg<Object> changePriceOrder(OrderCPVo vo) {
		OrderNode order = orderDao.selectoneOrderByOrderid(vo.getOrder_id());
		if (order==null) {
			return new ReqMsg<Object>(101,"无效订单",null);
		}
		
		//有订单必须为未付款状态，才可以修改价格
		if (order.getOrder_state()!=0) {
			return new ReqMsg<Object>(102,"订单状态不对",null);
		}
		
		Map<BigInteger,BigInteger> mapDetail = new HashMap<BigInteger,BigInteger>();
		List<OrderDetailCPVo> arr_detail = vo.getArr_detail();
		for (OrderDetailCPVo node : arr_detail) {
			mapDetail.put(node.getDetail_id(), node.getIntTotal_amount());
		}
		
		List<OrderDetailNode> listDetail = orderDao.selectlistOrderDetailByOrderid(vo.getOrder_id());
		if (listDetail==null || listDetail.size()==0) {
			return new ReqMsg<Object>(101,"无效订单",null);
		}
		 
		 Map<String,Object> paraMap = new HashMap<String,Object>();
		 
		 boolean isChange = false;
		
		 BigInteger orderPrice = BigInteger.ZERO;
		for (OrderDetailNode node : listDetail) {
			BigInteger total_amount = mapDetail.get(node.getDetail_id());
			if (total_amount!=null && !node.getTotal_amount().equals(total_amount)) {
				paraMap.clear();
				paraMap.put("detail_id", node.getDetail_id());
				paraMap.put("total_amount", total_amount);
				if (node.getFenrun_amount()!=null) {	//如果分成金额不为空，计算分成金额
					Goods goods = goodsDao.get(node.getGoods_id().toString());
					if (goods!=null) {
						Long divideAmount = (new BigDecimal(total_amount)
						.multiply(new BigDecimal(goods.getDivide_rate()).divide(new BigDecimal(100)))).longValue();
						paraMap.put("fenrun_amount", divideAmount);
					}
				}
				if (1!=orderDao.updateoneOrderDetailForChangpriceById(paraMap)) {
					throw new RuntimeException();
				}
				isChange = true;
			} else {
				total_amount = node.getTotal_amount();
			}
			orderPrice = orderPrice.add(total_amount);
		}
		
		BigInteger totalPrice = orderPrice;
		BigInteger transExp = vo.getIntTrans_exp();
		if (transExp!=null) {
			totalPrice = totalPrice.add(transExp);
			if (!transExp.equals(order.getTrans_exp())) {
				isChange = true;
			}
		}
		
		if (!isChange) {
			return new ReqMsg<Object>(0,"价格没有变化",null);
		}
		
		if (totalPrice.compareTo(BigInteger.ZERO)<0) {	//最终的总金额不能小于0
			throw new RuntimeException();
		}
		
		paraMap.clear();
		paraMap.put("order_id", vo.getOrder_id());
		paraMap.put("trans_exp",transExp);
		//paraMap.put("order_amount", orderPrice);
		paraMap.put("pay_amount", totalPrice);
		paraMap.put("is_change_price", 1);
		if (1!=orderDao.updateoneOrderForChangpriceById(paraMap)) {
			throw new RuntimeException();
		}
		
		return new ReqMsg<Object>(0,"修改价格成功",null);
	}

	/*************以下为退款业务*****************/
	
	/**
	 * 退款列表查询
	 */
	@Override
	public PageBean<OrderRefundVo> selectlistRefundByPage(int dpage, int dcount, Map<String, Object> paraMap) {
		
		int allcount = refundDao.selectcountRefundByPage(paraMap);
		dpage = (dpage-1)*dcount;
		paraMap.put("page", dpage);
		paraMap.put("count", dcount);
		List<OrderRefundNode> listOrder = refundDao.selectlistRefundByPage(paraMap);
		
		List<OrderRefundVo> result = new ArrayList<OrderRefundVo>();
		if (listOrder!=null) {
			for (OrderRefundNode node : listOrder) {
				if (node.getDetail_id()!=null) {
					OrderDetailNode detail = orderDao.selectoneOrderDetailById(node.getDetail_id());
					if (detail!=null) {
						OrderNode order = orderDao.selectoneOrderByOrderid(detail.getOrder_id());
						if (order!=null) {
							detail.setOrder_number(order.getOrder_number());
							result.add(new OrderRefundVo(node,detail));
						}
					}
				}
			}
		}

		PageListBean page = FuncAssem.caclPage(dpage,dcount,allcount);
		return new PageBean<OrderRefundVo>(page,result);
	}


	/**
	 * 退款详情
	 */
	@Override
	public ReqMsg<RefundVo> selectRefundDetailById(String refund_id) {
		OrderRefundNode refund = refundDao.selectoneRefundById(refund_id);
		if (refund==null) {
			return new ReqMsg<RefundVo>(101,"异常",null);
		}
		
		Account account = accountDao.getById(refund.getAccount_id());
		if (account==null) {
			return new ReqMsg<RefundVo>(101,"异常",null);
		}
		
		BigInteger detail_id = refund.getDetail_id();
		if (detail_id==null || detail_id.compareTo(BigInteger.ZERO)<=0) {
			return new ReqMsg<RefundVo>(101,"异常",null);
		}
		OrderDetailNode detail = orderDao.selectoneOrderDetailById(detail_id);
		if (detail==null) {
			return new ReqMsg<RefundVo>(101,"异常",null);
		}
		
		OrderNode order = orderDao.selectoneOrderByOrderid(detail.getOrder_id());
		if (order==null) {
			return new ReqMsg<RefundVo>(101,"异常",null);
		}
		
		List<OrderRefundLogsNode> arrlogs = refundDao.selectlistRefundLogsByRefundid(refund_id);
		
		String seller_pic = SysconfigFuc.getSysValue("cfg_mana_headpic");	//商家头像
		String seller_name = "运营商";	//商家名称（固定写0）

		return new ReqMsg<RefundVo>(0,"succ",new RefundVo(order,refund,detail,arrlogs, account, seller_pic, seller_name));
	}

	/**
	 * 退款（货）申批
	 * @throws RuntimeException 
	 */
	@Override
	@Transactional
	public synchronized ReqMsg<OrderPushNode> refundGrant(String refund_id, int grant_tag/*1：同意 2：拒绝*/, String record_explain, String opr) {
		//获取退款（货）对象
		OrderRefundNode refund = refundDao.selectoneRefundById(refund_id);
		if (refund==null) {
			return new ReqMsg<OrderPushNode>(101,"对象不存在",null);
		}
		
		//获取子订单对象
		BigInteger detail_id = refund.getDetail_id();
		if (detail_id==null) {
			return new ReqMsg<OrderPushNode>(101,"对象不存在",null);
		}
		OrderDetailNode detail = orderDao.selectoneOrderDetailById(detail_id);
		if (detail==null) {
			return new ReqMsg<OrderPushNode>(101,"对象不存在",null);
		}
		
		OrderNode order = orderDao.selectoneOrderByOrderid(detail.getOrder_id());
		if (order==null) {
			return new ReqMsg<OrderPushNode>(101,"对象不存在",null);
		}
		String orderNumber = order.getOrder_number();
		
		//获取会员账户信息
		BigInteger account_id = refund.getAccount_id();
		Account account = accountDao.getById(account_id);
		if (account==null) {
			return new ReqMsg<OrderPushNode>(101,"对象不存在",null);
		}
		
		//获取商家资金表
		BigInteger seller_id = refund.getSeller_id();
		ManaFunds manafunds = manaFundsDao.getBySellerid(seller_id);
		if (manafunds==null) {
			return new ReqMsg<OrderPushNode>(101,"对象不存在",null);
		}
		
		int detail_state = detail.getState();	/* 子订单状态
												0：未付款（等待买家付款） 
												1：等待卖家发货(买家已付款) 
												2：等待买家确认收货（卖家已发货） 
												3：交易成功 
												4： 退款中 
												5： 退款成功（交易关闭） 
												6： 未付款前，关闭交易 
												7: 退货退款中 
												8: 退货退款成功
												9: 售后中 
												10: 售后成功
												*/
												
		int refund_type = refund.getRefund_type();	/*	退款（货）单类型
														0：退款 
														1：退货 
														2：售后订单
													*/
		int refund_state = refund.getRefund_state();	/*	退款单状态
															1:待商家同意(申请退货退款) 
															2:取消申请 (退货退款)  
															3:同意申请(待买家发货) 
															4:已退货(待卖家同意) 
															5:拒绝申请 
															6:退货时间超时 
															7:同意退款(流程结束) 
														*/	
		
		long time_out = 0;
		
				
		
		String title = "", content = "";
		boolean isEnd = false;
		int new_detail_state = -1, new_refund_state = -1;
		if (refund_type==0)  {	//退款单
			if (refund_state==1 && detail_state==4) {
				title = "仅退款";
				if (grant_tag==1) { //同意
					new_detail_state = 5;	//退款成功
					new_refund_state = 7;	//同意退款
					isEnd = true;	//流程结束
					time_out = new Date().getTime() + 24*60*60*1000*Integer.valueOf(SysconfigFuc.getSysValue("cfg_order_onlyRefund"));
					//content = "你的退款单申请已受理成功，退款金额会返还到你的账户余额，请注意查收。>>";
					content = SysconfigFuc.getSysValue("cfg_refund_sure_push");
					content = content.replace("${type}",title);
				} else {	//拒绝
					new_detail_state = 1;	//等待卖家发货(买家已付款) 
					new_refund_state = 5;	//拒绝申请
					//content = "你的退款单申请已被商家拒绝，点击查看>>"; 
					content = SysconfigFuc.getSysValue("cfg_refuse_refund_push");
					content = content.replace("${type}",title);
				}
			}
		} else if (refund_type==1) {	//退货单
			title = "退货退款";
			if (refund_state==1 && detail_state==7) {	//退货阶段
				if (grant_tag==1) {
					new_refund_state = 3;	//同意申请(待买家发货)
					time_out = new Date().getTime() + 24*60*60*1000*Integer.valueOf(SysconfigFuc.getSysValue("cfg_order_goodsRefund"));
					//content = "你的退货退款单申请已商家已同意，请在"+str_time_out+"前回填物流单号，把货寄给给商家。>>";
					content = SysconfigFuc.getSysValue("cfg_refund_goods_push");
					content = content.replace("${type}",title);
					content = content.replace("{填写物流单号截止时间}",FuncAssem.timeToStr(new Timestamp(time_out)));
				} else {
					new_refund_state = 5;	//拒绝申请
					new_detail_state = 2;	//等待买家确认收货（卖家已发货） 
					//content = "你的退货退款单申请已被商家拒绝，点击查看>>";
					content = SysconfigFuc.getSysValue("cfg_refuse_refund_push");
					content = content.replace("${type}",title);
				}
			} else if (refund_state==4 && detail_state==7) {	//退款阶段
				if (grant_tag==1) {
					new_detail_state = 8;	//退货退款成功
					new_refund_state = 7;	//同意退款
					isEnd = true;	//流程结束
					time_out = new Date().getTime() + 24*60*60*1000*Integer.valueOf(SysconfigFuc.getSysValue("cfg_order_onlyRefund"));
					//content = "你的退货退款单申请已受理成功，退款金额会返还到你的账户余额，请注意查收。>>";
					content = SysconfigFuc.getSysValue("cfg_refund_sure_push");
					content = content.replace("${type}",title);
				} else {
					new_refund_state = 5;	//拒绝申请
					new_detail_state = 2;	//等待买家确认收货（卖家已发货） 
					//content = "你的退款单申请已被商家拒绝，点击查看>>";
					content = SysconfigFuc.getSysValue("cfg_refuse_refund_push");
					content = content.replace("${type}",title);
				}
			}
		} else if (refund_type==2) {	//售后单
			title = "售后";
			if (refund_state==1 && detail_state==9) {	//退货阶段
				if (grant_tag==1) {
					new_refund_state = 3;	//同意申请(待买家发货)
					time_out = new Date().getTime() + 24*60*60*1000*Integer.valueOf(SysconfigFuc.getSysValue("cfg_order_goodsRefund"));
					//content = "你的售后单申请已商家已同意，请在"+str_time_out+"前回填物流单号，把货寄给给商家。>>";
					content = SysconfigFuc.getSysValue("cfg_refund_goods_push");
					content = content.replace("${type}",title);
					content = content.replace("{填写物流单号截止时间}",FuncAssem.timeToStr(new Timestamp(time_out)));
				} else {
					new_refund_state = 5;	//拒绝申请
					new_detail_state = 3;	//交易成功 
					//content = "你的售后单申请已被商家拒绝，点击查看>>";
					content = SysconfigFuc.getSysValue("cfg_refuse_refund_push");
					content = content.replace("${type}",title);
				}
			} else if (refund_state==4 && detail_state==9) {	//退款阶段
				if (grant_tag==1) {
					new_detail_state = 10;	//售后成功
					new_refund_state = 7;	//同意退款
					isEnd = true;	//流程结束
					time_out = new Date().getTime() + 24*60*60*1000*Integer.valueOf(SysconfigFuc.getSysValue("cfg_order_onlyRefund"));
					//content = "你的售后单申请已受理成功，退款金额会返还到你的账户余额，请注意查收。>>";
					content = SysconfigFuc.getSysValue("cfg_refund_sure_push");
					content = content.replace("${type}",title);
				} else {
					new_refund_state = 5;	//拒绝申请
					new_detail_state = 3;	//交易成功 
					//content = "你的售后单申请已被商家拒绝，点击查看>>";
					content = SysconfigFuc.getSysValue("cfg_refuse_refund_push");
					content = content.replace("${type}",title);
				}
			}
		}
		if (new_refund_state==-1) {
			return new ReqMsg<OrderPushNode>(111,"订单状态不对，不能审批",null);
		}
		
		if (isEnd) {
			BigInteger sellertotal = manafunds.getTotal_assets();	//商家总资金
			BigInteger sellerBalance = manafunds.getBalance();	//商家可用资金资金
			BigInteger buyerBalance = account.getBalance();	//买家资金
			BigInteger amount = refund.getRefund_amount();
			if (	sellerBalance==null || sellerBalance.compareTo(BigInteger.ZERO)<0 ||
					buyerBalance==null || buyerBalance.compareTo(BigInteger.ZERO)<0 ||
					amount==null || amount.compareTo(BigInteger.ZERO)<0 ||
					sellerBalance.compareTo(amount)<0 ||
					sellertotal.compareTo(sellerBalance)<0) {
				return new ReqMsg<OrderPushNode>(112,"商户余额不足",null);
			}
		}
		//添加记录表
		Timestamp curTime = new Timestamp(new Date().getTime());
		OrderRefundLogsNode refundLogs = new OrderRefundLogsNode(BigInteger.ZERO,refund.getRefund_id(),refund.getSeller_nick(),refund.getBuyer_nick(),"",record_explain,"",curTime,1);
		if (1!=refundDao.insertoneRefundLogs(refundLogs)) {
			throw new RuntimeException();
		}

		//更改退款（货）表状态
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("refund_id", refund_id);
		paraMap.put("refund_state", new_refund_state);
		paraMap.put("refund_end_time", new Timestamp(new Date().getTime()));
		if (time_out>0)
			paraMap.put("time_out", new Timestamp(time_out));
		if (1!=refundDao.updateoneRefundForGrant(paraMap)) {
			throw new RuntimeException();
		}
		
		if (new_detail_state!=-1) {	//子订单状态有更改
			paraMap.clear();
			paraMap.put("detail_id", detail_id);
			paraMap.put("state", new_detail_state);
			if (1!=orderDao.updateoneOrderDetailForStateById(paraMap)) {
				throw new RuntimeException();
			}
		}
		
		//资金处理
		if (isEnd) {
			BigInteger sellertotal = manafunds.getTotal_assets();	//商家总资金
			BigInteger sellerBalance = manafunds.getBalance();	//商家可用资金资金
			BigInteger buyerBalance = account.getBalance();	//买家资金
			BigInteger amount = refund.getRefund_amount();
			if (	sellerBalance==null || sellerBalance.compareTo(BigInteger.ZERO)<0 ||
					buyerBalance==null || buyerBalance.compareTo(BigInteger.ZERO)<0 ||
					amount==null || amount.compareTo(BigInteger.ZERO)<0 ||
					sellerBalance.compareTo(amount)<0 ||
					sellertotal.compareTo(sellerBalance)<0) {
				throw new RuntimeException();
			}
			//扣除商家资金
			sellertotal = sellertotal.subtract(amount);
			sellerBalance = sellerBalance.subtract(amount);
			paraMap.clear();
			paraMap.put("fund_id", manafunds.getFund_id());
			paraMap.put("total_assets", sellertotal);
			paraMap.put("balance", sellerBalance);
			if (1!=manaFundsDao.updateoneBalanceById(paraMap)) {
				throw new RuntimeException();
			}
			//添加商家资金流水记录
			BillFlow billFlow = new BillFlow(
					BigInteger.ZERO,
					manafunds.getSeller_id(),
					RandomCharUtil.randOrderNumber(),
					amount,
					0,
					curTime,
					"1",
					"退款",
					"1",
					"余额支付",
					orderNumber,//refund_id,
					"",
					opr,
					curTime);
			billFlowDao.insert(billFlow);

			
			//增加会员账户余额
			buyerBalance = buyerBalance.add(amount);
			paraMap.put("id", account.getId());
			paraMap.put("balance", buyerBalance);
			if (1!=accountDao.updateoneBalanceById(paraMap)) {
				throw new RuntimeException();
			}
			//添加会员账户账单记录
			AccountBill accountBill = new AccountBill(
					BigInteger.ZERO,
					curTime,
					RandomCharUtil.randOrderNumber(),
					account.getId(),
					"商品退款",
					String.valueOf(detail.getOrder_id()),//refund_id,
					1,
					4,
					2,
					amount,
					"");
			accountBillDao.insert(accountBill);
		}
//		
//		//短信通知客户
//		//Account account = accountDao.getById(order.getAccount_id());
//		if (account!=null) {
//			SmsClient.send(account.getMobile(), content);
//		}
//		
//		//系统推送
//		if (account.getPush_code()!=null) {
//			JiguangClient.send(new JPushBean(account.getPush_code(),title,content,refund.getRefund_id(),"0",opr));
//		}

		return new ReqMsg<OrderPushNode>(0,"处理成功",new OrderPushNode(account.getMobile(),account.getPush_code(),content,content,refund.getRefund_id(),"2",String.valueOf(account.getId())));
	}
	
	/**
	 * 改变主订单状态
	 * @param refund_id 退款（货）单主键ID
	 */
	public boolean editMainOrderState(String refund_id) {
		//获取退款（货）对象
		OrderRefundNode refund = refundDao.selectoneRefundById(refund_id);
		if (refund==null) {
			return false;
		}
		
		//获取子订单对象
		BigInteger detail_id = refund.getDetail_id();
		if (detail_id==null) {
			return false;
		}
		OrderDetailNode detail = orderDao.selectoneOrderDetailById(detail_id);
		if (detail==null) {
			return false;
		}
		
		
		//获取主订单对象
		BigInteger order_id = detail.getOrder_id();
		if (order_id==null) {
			return false;
		}
		OrderNode order = orderDao.selectoneOrderByOrderid(order_id);
		if (order==null) {
			return false;
		}
		
		//获取主订单下的所有子订单对象
		List<OrderDetailNode> listDetail = orderDao.selectlistOrderDetailByOrderid(order_id);
		if (listDetail==null || listDetail.size()==0) {
			return false;
		}
		

		boolean isUpdate = false;
		int state = -1;
		if (	isStateSample(listDetail,5,8,10,5)/* ||
				isStateSample(listDetail,8) ||
				isStateSample(listDetail,10)*/
				) {
			isUpdate = true;
			state = 5;
		} else if (isStateSample2(listDetail,6)) {
			isUpdate = true;
			state = 6;
		}
			
		
		/* 主订单状态
			0：待付款
			1：待卖家发货
			2：待确认收货
			3：交易成功
			5： 交易关闭
			6： 交易关闭
		*/
		
		if (isUpdate) {
			Map<String,Object> paraMap = new HashMap<String,Object>();
			paraMap.put("order_id", order_id);
			paraMap.put("order_state", state);
			if (1!=orderDao.updateoneOrderForOrderstate(paraMap)) {
				return false;
			}
		}
		return true;
		
	}
	
	private static boolean isStateSample(List<OrderDetailNode> listDetail, int state1, int state2, int state3, int state4) {
		boolean isSample = true;
		for (OrderDetailNode node : listDetail) {	
			int _state = node.getState();
			if (_state!=state1 && _state!=state2 && _state!=state3 && _state!=state4) {
				isSample = false;
				break;
			}
		}
		return isSample;
	}
	
	private static boolean isStateSample2(List<OrderDetailNode> listDetail, int state) {
		boolean isSample = true;
		for (OrderDetailNode node : listDetail) {	
			int _state = node.getState();
			if (_state!=state) {
				isSample = false;
				break;
			}
		}
		return isSample;
	}
	/*************以上为退款业务*****************/
	
	

	@Override
	public ReqMsg<ExpressInfo> lookupShip(String order_number) {
		OrderExpressNode orderExpress = orderDao.selectonOrderExpressByOrderid(order_number);
		if (orderExpress==null) {
			return new ReqMsg<ExpressInfo>(101,"无效订单",null);
		}
		Timestamp last_update_time = orderExpress.getLast_update_time();
		if (last_update_time==null) {
			return new ReqMsg<ExpressInfo>(106,"暂无物流信息",null);
		}
		String fast_waybill = orderExpress.getFast_waybill();
		if (StringUtils.isBlank(fast_waybill)) {
			return new ReqMsg<ExpressInfo>(102,"订单号为空",null);
		}
		String fast_code = orderExpress.getFast_code();
		if (StringUtils.isBlank(fast_code)) {
			fast_code = "auto";	//置为自动识别
		}
		int logistics_state = orderExpress.getLogistics_state();
		
		String fast_name = "",fast_phone="";
		//String fast_waybill;		//快递单号
		List<LogisticsChildNode> arrLogistics = null;
		long lastTime = last_update_time.getTime();
		long curTime = new Date().getTime();
		if (logistics_state==4 || lastTime+3600000>curTime) {	//如果已签收  或  一个小时内的，直接取记录
			String logistics_record = orderExpress.getLogistics_record();
			fast_name = orderExpress.getFast_name();
			fast_phone = orderExpress.getFast_phone();
			arrLogistics = JsonUtil.strToJson(logistics_record,new TypeToken<List<LogisticsChildNode>>() {}.getType());
		} else {	//超过一个小时的，从物流接口上去取
			ReqMsg<LogisticsNode> res = Logistics.get(fast_code, fast_waybill);
			if (res==null) {
				return new ReqMsg<ExpressInfo>(103,"异常",null);
			}
			if (res.getCode()!=0 || res.getInfo()==null) {
				return new ReqMsg<ExpressInfo>(res.getCode(),res.getDesc(),null);
			}
			
			LogisticsNode lres = res.getInfo();
			String expSpellName = lres.getExpSpellName();
			String expTextName = lres.getExpTextName();
			int status = lres.getStatus();
			String updateStr = lres.getUpdateStr();
			arrLogistics = lres.getData();
			String strData = JsonUtil.jsonToStr(arrLogistics);
			
			Map<String,Object> paraMap = new HashMap<String,Object>();
			paraMap.put("oe_id", orderExpress.getOe_id());
			paraMap.put("fast_code", expSpellName);
			paraMap.put("fast_name", expTextName);
			paraMap.put("logistics_state", status);
			paraMap.put("logistics_record", strData);
			paraMap.put("fast_phone", lres.getTel());
			paraMap.put("updateStr", updateStr);
			paraMap.put("last_update_time", new Timestamp(curTime));
			if (1!=orderDao.updateoneOrderExpressForExpress(paraMap)) {
				return new ReqMsg<ExpressInfo>(103,"异常",null);
			}
			
			fast_name = expTextName;
		}
		
		if (arrLogistics==null) {
			return new ReqMsg<ExpressInfo>(103,"异常",null);
		} else {
			return new ReqMsg<ExpressInfo>(0,"succ",new ExpressInfo(fast_name,fast_waybill,arrLogistics));
		}
	}


	@Override
	public int getcountOrderExpressByFastcode(String fast_code) {
		return orderDao.getcountOrderExpressByFastcode(fast_code);
	}


	@Override
	public OrderNode getOrderByOrderNumber(String order_number) {
		return orderDao.selectoneOrderByOrderNumber(order_number);
	}


	@Override
	public OrderDetailNode getOrderDetailByDetailNumber(String detail_number) {
		return orderDao.selectoneOrderDetailByNumber(detail_number);
	}


	@Override
	public List<OrderDetailNode> getlistOrderDetailByDetailNumber(String order_number) {
		OrderNode order = orderDao.selectoneOrderByOrderNumber(order_number);
		if (order==null) 
			return null;
		return orderDao.selectlistOrderDetailByOrderid(order.getOrder_id());
	}



}

