package com.aten.model.vo;

import java.io.Serializable;

import com.aten.model.orm.OrderDetailNode;
import com.aten.model.orm.OrderRefundLogsNode;
import com.aten.model.orm.OrderRefundNode;

public class RefundGrantVo implements Serializable {
	private static final long serialVersionUID = 4555659774784583548L;
	
	private OrderRefundNode refund;
	private OrderDetailNode detail;
	private OrderRefundLogsNode refundlogs;
	public RefundGrantVo() {
		super();
	}
	public RefundGrantVo(OrderRefundNode refund, OrderDetailNode detail, OrderRefundLogsNode refundlogs) {
		super();
		this.refund = refund;
		this.detail = detail;
		this.refundlogs = refundlogs;
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
	public OrderRefundLogsNode getRefundlogs() {
		return refundlogs;
	}
	public void setRefundlogs(OrderRefundLogsNode refundlogs) {
		this.refundlogs = refundlogs;
	}
}
