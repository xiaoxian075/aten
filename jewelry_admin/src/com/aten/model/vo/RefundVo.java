package com.aten.model.vo;

import java.io.Serializable;
import java.util.List;

import com.aten.model.orm.Account;
import com.aten.model.orm.OrderDetailNode;
import com.aten.model.orm.OrderNode;
import com.aten.model.orm.OrderRefundLogsNode;
import com.aten.model.orm.OrderRefundNode;

public class RefundVo implements Serializable {
	private static final long serialVersionUID = 7354969084696433515L;
	
	private OrderNode order;
	private OrderRefundNode refund;
	private OrderDetailNode detail;
	private List<OrderRefundLogsNode> arrlogs;
	private Account account;
	private String seller_pic;
	private String seller_name;
	public RefundVo() {
		super();
	}
	public RefundVo(OrderNode order, OrderRefundNode refund, OrderDetailNode detail, List<OrderRefundLogsNode> arrlogs, Account account, String seller_pic, String seller_name) {
		super();
		this.order = order;
		this.refund = refund;
		this.detail = detail;
		this.arrlogs = arrlogs;
		this.account = account;
		this.seller_pic = seller_pic;
		this.seller_name = seller_name;
	}
	public OrderNode getOrder() {
		return order;
	}
	public void setOrder(OrderNode order) {
		this.order = order;
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
	public List<OrderRefundLogsNode> getArrlogs() {
		return arrlogs;
	}
	public void setArrlogs(List<OrderRefundLogsNode> arrlogs) {
		this.arrlogs = arrlogs;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getSeller_pic() {
		return seller_pic;
	}
	public void setSeller_pic(String seller_pic) {
		this.seller_pic = seller_pic;
	}
	public String getSeller_name() {
		return seller_name;
	}
	public void setSeller_name(String seller_name) {
		this.seller_name = seller_name;
	}
}
