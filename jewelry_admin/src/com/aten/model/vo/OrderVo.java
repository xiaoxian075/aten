package com.aten.model.vo;

import java.io.Serializable;
import java.util.List;

import com.aten.model.orm.OrderAuxiliary;
import com.aten.model.orm.OrderDeposit;
import com.aten.model.orm.OrderDetailNode;
import com.aten.model.orm.OrderExpressNode;
import com.aten.model.orm.OrderFullbook;
import com.aten.model.orm.OrderNode;

public class OrderVo implements Serializable {
	private static final long serialVersionUID = -6787694434380312561L;
	
	private OrderNode order;
	private List<OrderDetailNode> detail;
	private OrderFullbook fullbook;	//全额销售
	private OrderDeposit depositbook;//预付销售
	private OrderExpressNode express;
	private OrderAuxiliary auxiliary;	//记录订单发票信息
	public OrderVo() {
		super();
	}
	public OrderVo(OrderNode order, List<OrderDetailNode> detail, OrderFullbook fullbook, OrderDeposit depositbook, OrderExpressNode express, OrderAuxiliary auxiliary) {
		super();
		this.order = order;
		this.detail = detail;
		this.fullbook = fullbook;
		this.depositbook = depositbook;
		this.express = express;
		this.auxiliary = auxiliary;
	}
	public OrderNode getOrder() {
		return order;
	}
	public void setOrder(OrderNode order) {
		this.order = order;
	}
	public List<OrderDetailNode> getDetail() {
		return detail;
	}
	public void setDetail(List<OrderDetailNode> detail) {
		this.detail = detail;
	}
	public OrderFullbook getFullbook() {
		return fullbook;
	}
	public void setFullbook(OrderFullbook fullbook) {
		this.fullbook = fullbook;
	}
	public OrderDeposit getDepositbook() {
		return depositbook;
	}
	public void setDepositbook(OrderDeposit depositbook) {
		this.depositbook = depositbook;
	}
	public OrderExpressNode getExpress() {
		return express;
	}
	public void setExpress(OrderExpressNode express) {
		this.express = express;
	}
	public OrderAuxiliary getAuxiliary() {
		return auxiliary;
	}
	public void setAuxiliary(OrderAuxiliary auxiliary) {
		this.auxiliary = auxiliary;
	}
}
