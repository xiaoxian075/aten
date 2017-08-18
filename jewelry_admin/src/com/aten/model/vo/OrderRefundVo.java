package com.aten.model.vo;

import java.io.Serializable;

import com.aten.model.orm.OrderDetailNode;
import com.aten.model.orm.OrderRefundNode;

public class OrderRefundVo implements Serializable {
	private static final long serialVersionUID = -8910472374774767121L;
	
	private OrderRefundNode refund;
	private OrderDetailNode detail;
	public OrderRefundVo() {
		super();
	}
	public OrderRefundVo(OrderRefundNode refund, OrderDetailNode detail) {
		super();
		this.refund = refund;
		this.detail = detail;
	}
	public OrderRefundNode getRefund() {
		return refund;
	}
	public void setRefund(OrderRefundNode refund) {
		this.refund = refund;
	}
	public OrderDetailNode getDetail() {
		return detail;
	}
	public void setDetail(OrderDetailNode detail) {
		this.detail = detail;
	}
}
