package com.aten.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.aten.model.bean.ExpressInfo;
import com.aten.model.bean.OrderPushNode;
import com.aten.model.bean.PageBean;
import com.aten.model.orm.OrderDetailNode;
import com.aten.model.orm.OrderNode;
import com.aten.model.vo.OrderCPVo;
import com.aten.model.vo.OrderRefundVo;
import com.aten.model.vo.OrderVo;
import com.aten.model.vo.RefundVo;
import com.communal.node.ReqMsg;

public interface OrderService {

	PageBean<OrderVo> selectlistOrderByPage(int dpage,int dcount,Map<String, Object> paraMap);

	ReqMsg<Object> sendGoods(BigInteger order_id, String fast_code, String fast_waybill, String opr);

	ReqMsg<Object> changePriceOrder(OrderCPVo vo);

	ReqMsg<ExpressInfo> lookupShip(String order_number);

	ReqMsg<OrderVo> selectOrderById(BigInteger order_id);

	PageBean<OrderRefundVo> selectlistRefundByPage(int dpage, int dcount, Map<String, Object> paraMap);

	ReqMsg<RefundVo> selectRefundDetailById(String refund_id);

	ReqMsg<OrderPushNode> refundGrant(String refund_id, int grant_tag, String record_explain, String opr);

	boolean editMainOrderState(String refund_id);
	
	int getcountOrderExpressByFastcode(String fastCode);

	OrderNode getOrderByOrderNumber(String order_number);

	OrderDetailNode getOrderDetailByDetailNumber(String detail_number);

	List<OrderDetailNode> getlistOrderDetailByDetailNumber(String order_number);

}
