package com.aten.controller;


import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aten.client.JiguangClient;
import com.aten.client.SmsClient;
import com.aten.function.SysconfigFuc;
import com.aten.model.bean.ExpressInfo;
import com.aten.model.bean.JPushBean;
import com.aten.model.bean.OrderPushNode;
import com.aten.model.bean.PageBean;
import com.aten.model.orm.Account;
import com.aten.model.orm.OrderDetailNode;
import com.aten.model.orm.OrderNode;
import com.aten.model.orm.OrderRefundNode;
import com.aten.model.vo.OrderCPVo;
import com.aten.model.vo.OrderDetailCPVo;
import com.aten.model.vo.OrderRefundVo;
import com.aten.model.vo.OrderVo;
import com.aten.model.vo.RefundVo;
import com.aten.service.AccountService;
import com.aten.service.OrderService;
import com.communal.constants.Constant;
import com.communal.node.ReqMsg;
import com.communal.util.Func;
import com.communal.util.JsonUtil;
import com.communal.util.StringUtil;


/**
 * @author chenjx
 * @Function 订单类  controller
 * @date 2017-07-13 09:39:53
 */

@Controller
@RequestMapping("admin/order")
public class OrderController {
	private static final Logger logger = Logger.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * 查询订单记录
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request,Model model){
		
		/*参数验证*/
		String sdpage = request.getParameter("dpage");
		String sdcount = request.getParameter("dcount");
		int dpage = 1,dcount=10;	//默认值
		if (!StringUtils.isBlank(sdpage)) {
			int v = Integer.valueOf(sdpage);
			if (v>0 && v<100000) {
				dpage = v;
			}
		}
		if (!StringUtils.isBlank(sdcount)) {
			int v = Integer.valueOf(sdcount);
			if (v>0 && v<100000) {
				dcount = v;
			}
		}
		

		String order_number = request.getParameter("order_number");
		String sorder_type = request.getParameter("order_type");
		String sorder_state = request.getParameter("order_state");
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
		String sis_bill = request.getParameter("is_bill");
		Map<String,Object> paraMap = new HashMap<String,Object>();
		if (!StringUtils.isBlank(order_number)) {
			paraMap.put("order_number", order_number);
			model.addAttribute("order_number",order_number);
		}
		if (!StringUtils.isBlank(sorder_type)) {
			Integer v = Integer.valueOf(sorder_type);
			if (v>=0 && v!=100) {
				paraMap.put("order_type", v);
				model.addAttribute("order_type",v);
			}
		}
		if (!StringUtils.isBlank(sorder_state)) {
			Integer v = Integer.valueOf(sorder_state);
			if (v>=0 && v!=100) {
				paraMap.put("order_state", v);
				model.addAttribute("order_state",v);
			}
		}
		if (start_time!=null && start_time.length()==10) {
			model.addAttribute("start_time",start_time);
			start_time += " 00:00:00";
			Timestamp ts = Timestamp.valueOf(start_time);
			System.out.println(ts);
			paraMap.put("start_time", ts);
		}
		if (end_time!=null && end_time.length()==10) {
			model.addAttribute("end_time",end_time);
			end_time += " 23:59:59";
			Timestamp ts = Timestamp.valueOf(end_time);
			System.out.println(ts);
			paraMap.put("end_time", ts);
		}
		if (!StringUtils.isBlank(sis_bill)) {
			Integer v = Integer.valueOf(sis_bill);
			if (v>=0 && v!=100) {
				paraMap.put("is_bill", v);
				model.addAttribute("is_bill",v);
			}
		}

		PageBean<OrderVo> pageBean = orderService.selectlistOrderByPage(dpage,dcount,paraMap);
		
		List<OrderVo> listVo = pageBean.getArrList();
		for (OrderVo node : listVo) {
			BigInteger account_id = node.getOrder().getAccount_id();
			if (account_id!=null) {
				Account account = accountService.getById(account_id);
				if (account!=null) {
					node.getOrder().setAccount_name(account.getLogin_name());
				}
			}
		}
		
		model.addAttribute("ossImgServerUrl",SysconfigFuc.getSysValue("oss_imgurl"));//oss地址前缀
		model.addAttribute("page", pageBean.getPage());	//分页信息
		model.addAttribute("orderVo", listVo);
		
		return "order/list";
	}
	
	/**
	 * 查询记录详情
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("orderdetail")
	public String orderdetail(HttpServletRequest request,Model model){
		String s_order_id = request.getParameter("order_id");
		if (StringUtil.isBlank(s_order_id)) {
			return list(request,model);
		}
		BigInteger order_id = new BigInteger(s_order_id);
		if (order_id.compareTo(BigInteger.ZERO)<=0) {
			return list(request,model);
		}
		
		ReqMsg<OrderVo> reqmsg = orderService.selectOrderById(order_id);
		if (reqmsg==null || reqmsg.getCode()!=0) {
			return list(request,model);
		}
		
		OrderVo _orderVo = reqmsg.getInfo();
		if (_orderVo!=null) {
			OrderNode order = _orderVo.getOrder();
			if (order!=null) {
				Account account = accountService.getById(order.getAccount_id());
				if (account!=null) {
					order.setAccount_name(account.getLogin_name());
				}
			}
		}
		
		model.addAttribute("ossImgServerUrl",SysconfigFuc.getSysValue("oss_imgurl"));//oss地址前缀
		model.addAttribute("orderVo", reqmsg.getInfo());
		return "order/detail";
	}
	
	/**
	 * 发货
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("sendgoods")
	@ResponseBody
	public String sendgoods(HttpServletRequest request,Model model){
		String opr = (String)request.getSession().getAttribute(Constant.USER_NAME);
		
		String s_order_id = request.getParameter("order_id");
		String fast_code = request.getParameter("fast_code");
		String fast_waybill = request.getParameter("fast_waybill");
		if (StringUtil.isBlank(s_order_id) || StringUtil.isBlank(fast_code) || StringUtil.isBlank(fast_waybill)) {
			return JsonUtil.jsonToStr(new ReqMsg<String>(1,"失败",null));
		}
		BigInteger order_id = new BigInteger(s_order_id);
		if (order_id.compareTo(BigInteger.ZERO)<=0) {
			return JsonUtil.jsonToStr(new ReqMsg<String>(1,"失败",null));
		}
		
		ReqMsg<Object> msg = null;
		try {
			msg = orderService.sendGoods(order_id,fast_code,fast_waybill,opr);
		} catch (Exception e) {
			logger.error(e);
		}
		if (msg==null) {
			return JsonUtil.jsonToStr(new ReqMsg<String>(1,"失败",null));
		}
		
		return JsonUtil.jsonToStr(msg);
	}
	
	/**
	 * 修改价格
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("changeprice")
	@ResponseBody
	public String changeprice(HttpServletRequest request,Model model){
		String data = request.getParameter("data");
		if (data==null || data.length()==0) {
			return JsonUtil.jsonToStr(new ReqMsg<Object>(1,"参数错误",null));
		}
		
		ReqMsg<?> msg = null;
		try {
			OrderCPVo vo = JsonUtil.strToJson(data, OrderCPVo.class);
			if (vo==null) {
				return JsonUtil.jsonToStr(new ReqMsg<Object>(1,"参数错误",null));
			}
			BigInteger order_id = vo.getOrder_id();
			BigInteger trans_exp = vo.getIntTrans_exp();
			List<OrderDetailCPVo> arr_detail = vo.getArr_detail();
			if (	order_id.compareTo(BigInteger.ZERO)<=0 ||
					trans_exp.compareTo(BigInteger.ZERO)<0 ||
					trans_exp.compareTo(new BigInteger("99999999"))>0 ||
					arr_detail==null) {
				return JsonUtil.jsonToStr(new ReqMsg<Object>(1,"参数错误",null));
			}
			for (OrderDetailCPVo dvo : arr_detail) {
				if (dvo==null) {
					return JsonUtil.jsonToStr(new ReqMsg<Object>(1,"参数错误",null));
				}
				BigInteger detail_id = dvo.getDetail_id();
				BigInteger total_amount = dvo.getIntTotal_amount();
				if (detail_id==null || detail_id.compareTo(BigInteger.ZERO)<=0 ||
						total_amount==null || total_amount.compareTo(BigInteger.ZERO)<0 || total_amount.compareTo(new BigInteger("99999999"))>0) {
					return JsonUtil.jsonToStr(new ReqMsg<Object>(1,"参数错误",null));
				}
			}
			
			
			msg = orderService.changePriceOrder(vo);
			
		} catch (Exception e) {
			logger.error(e);
		}
		
		if (msg==null) {
			return JsonUtil.jsonToStr(new ReqMsg<Object>(2,"异常",null));
		} else {
			return JsonUtil.jsonToStr(msg);
		}
	}
	
	
	/*************以下为退款接口*****************/
	
	
	
	/**
	 * 查询订单记录
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("refundlist")
	public String refundlist(HttpServletRequest request,Model model){
		
		/*参数验证*/
		String sdpage = request.getParameter("dpage");
		String sdcount = request.getParameter("dcount");
		int dpage = 1,dcount=10;	//默认值
		if (!StringUtils.isBlank(sdpage)) {
			int v = Integer.valueOf(sdpage);
			if (v>0 && v<100000) {
				dpage = v;
			}
		}
		if (!StringUtils.isBlank(sdcount)) {
			int v = Integer.valueOf(sdcount);
			if (v>0 && v<100000) {
				dcount = v;
			}
		}

		String refund_id = request.getParameter("refund_id");			//退款编号
		String sorder_number = request.getParameter("detail_id");			//子订单编号
		String srefund_type = request.getParameter("refund_type");		//退款类型  0：退款 1：退货 2：售后订单
		String srefund_state = request.getParameter("refund_state");	//退款状态  0:退款中 1：退款成功 2：退款失败 
		Map<String,Object> paraMap = new HashMap<String,Object>();
		if (!StringUtils.isBlank(refund_id)) {
			paraMap.put("refund_id", refund_id);
			model.addAttribute("refund_id",refund_id);
		}
		if (!StringUtils.isBlank(sorder_number) && sorder_number.length()<32) {
			List<OrderDetailNode> arrOrderDetail = orderService.getlistOrderDetailByDetailNumber(sorder_number);
			List<BigInteger> arrDetailId = new ArrayList<BigInteger>();
			if (arrOrderDetail!=null) {
				for (OrderDetailNode node : arrOrderDetail) {
					arrDetailId.add(node.getDetail_id());
				}
			} else {
				arrDetailId.add(new BigInteger("0"));
			}
			paraMap.put("detail_id", arrDetailId);
			model.addAttribute("detail_id",sorder_number);
		}
		if (!StringUtils.isBlank(srefund_type)) {
			Integer v = Integer.valueOf(srefund_type);
			if (v>=0 && v<=2) {
				paraMap.put("refund_type", v);
				model.addAttribute("refund_type",srefund_type);
			}
		}
		if (!StringUtils.isBlank(srefund_state)) {
			Integer v = Integer.valueOf(srefund_state);
			if (v>=1 && v<=7) {
				paraMap.put("refund_state", v);
				model.addAttribute("refund_state",srefund_state);
			}
		}
		
		
		

		PageBean<OrderRefundVo> pageBean = orderService.selectlistRefundByPage(dpage,dcount,paraMap);
		List<OrderRefundVo> _listVo = pageBean.getArrList();
		if (_listVo!=null) {
			for (OrderRefundVo node : _listVo) {
				OrderRefundNode _refund = node.getRefund();
				BigInteger account_id = _refund.getAccount_id();
				Account account = accountService.getById(account_id);
				if (account!=null) {
					_refund.setAccount_name(account.getLogin_name());
				}
			}
		}
		
		model.addAttribute("ossImgServerUrl",SysconfigFuc.getSysValue("oss_imgurl"));//oss地址前缀
		model.addAttribute("page", pageBean.getPage());	//分页信息
		model.addAttribute("refundList", pageBean.getArrList());
		
		return "order/refundlist";
	}
	
	/**
	 * 查看售后订单详情
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("refunddetailpage")
	public String refunddetailpage(HttpServletRequest request,Model model){
		String refund_id = request.getParameter("refund_id");
		if (StringUtil.isBlank(refund_id)) {
			return refundlist(request,model);
		}
		
		ReqMsg<RefundVo> reqmsg = null;
		try {
			reqmsg = orderService.selectRefundDetailById(refund_id);
		} catch (Exception e) {
			reqmsg = null;
			logger.error(e);
		}
		
		if (reqmsg==null || reqmsg.getCode()!=0) {
			return refundlist(request,model);
		}
		
		model.addAttribute("ossImgServerUrl",SysconfigFuc.getSysValue("oss_imgurl"));//oss地址前缀
		model.addAttribute("cfgDefaultHeaderIng",SysconfigFuc.getSysValue("cfg_default_headerImg"));
		model.addAttribute("refundVo", reqmsg.getInfo());
		return "order/refunddetail";
	}
	
	/**
	 * 售后订单申批信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("refundgrantpage")
	public String refundgrantpage(HttpServletRequest request,Model model){
		String refund_id = request.getParameter("refund_id");
		if (StringUtil.isBlank(refund_id)) {
			return refundlist(request,model);
		}
		
		ReqMsg<RefundVo> reqmsg = null;
		try {
			reqmsg = orderService.selectRefundDetailById(refund_id);
		} catch (Exception e) {
			reqmsg = null;
			logger.error(e);
		}
		
		if (reqmsg==null || reqmsg.getCode()!=0) {
			return refundlist(request,model);
		}
		
		model.addAttribute("ossImgServerUrl",SysconfigFuc.getSysValue("oss_imgurl"));//oss地址前缀
		model.addAttribute("cfgDefaultHeaderIng",SysconfigFuc.getSysValue("cfg_default_headerImg"));
		model.addAttribute("refundVo", reqmsg.getInfo());
		return "order/refundgrant";
	}

	/**
	 * 售后订单申批
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("refundgrant")
	@ResponseBody
	public String refundgrant(HttpServletRequest request,Model model){
		String opr = (String)request.getSession().getAttribute(Constant.USER_NAME);
		
		String refund_id = request.getParameter("refund_id");
		String sgrant_tag = request.getParameter("grant_tag");
		String record_explain = request.getParameter("record_explain");
		if (
				StringUtil.isBlank(refund_id) || 
				StringUtil.isBlank(sgrant_tag) ||
				!Func.isNumeric(sgrant_tag) ||
				StringUtil.isBlank(record_explain)) {
			return JsonUtil.jsonToStr(new ReqMsg<Object>(1,"参数错误",null));
		}
		int grant_tag = Integer.valueOf(sgrant_tag);
		if (grant_tag<1 || grant_tag>2) {
			return JsonUtil.jsonToStr(new ReqMsg<Object>(1,"参数错误",null));
		}

		ReqMsg<OrderPushNode> reqmsg = null;
		try {
			reqmsg = orderService.refundGrant(refund_id,grant_tag,record_explain,opr);
		} catch (Exception e) {
			reqmsg = null;
			logger.error(e);
		}
		
		if (reqmsg==null) {
			reqmsg = new ReqMsg<OrderPushNode>(2,"异常",null);
		} else if (reqmsg.getCode()==0) { 	//遍历所有子订单状态是否为完成，改变主订单状态
			if (orderService.editMainOrderState(refund_id)) {
				if (reqmsg.getInfo()!=null)
					orderTask(reqmsg.getInfo());
			}
		}

		return JsonUtil.jsonToStr(reqmsg);
	}
	

	private static void orderTask(OrderPushNode push) {
		Runnable runnable = new Runnable() {
			public void run(){
				//短信通知客户
				if (push.getMobile()!=null) {
					SmsClient.send(push.getMobile(), push.getContent());
				}

				//系统推送
				if (push.getPushCode()!=null) {
					JiguangClient.send(new JPushBean(push.getPushCode(),push.getTitle(),push.getContent(),push.getOrderId(),push.getType(),push.getOpr()));
				}
			}
		};

		Thread thread = new Thread(runnable);
		thread.start();
	}


	
	
	/*************以上为退款接口*****************/
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 查看物流
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("lookupship")
	@ResponseBody
	public String lookupship(HttpServletRequest request,Model model){
		String order_number = request.getParameter("order_number");
		if (order_number==null || order_number.length()==0) {
			return JsonUtil.jsonToStr(new ReqMsg<Object>(1,"参数错误",null));
		}
		
		ReqMsg<ExpressInfo> msg = orderService.lookupShip(order_number);
		if (msg==null) {
			return JsonUtil.jsonToStr(new ReqMsg<ExpressInfo>(2,"异常",null));
		}
		
		return JsonUtil.jsonToStr(msg);
	}
}
