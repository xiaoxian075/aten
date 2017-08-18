package com.aten.model.orm;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

public class OrderTransNode implements Serializable {
	private static final long serialVersionUID = 7089378759966307624L;
	
	private BigInteger trans_id;	//自增	
	private BigInteger order_id;	//订单号
	private BigInteger account_id;	//买家标识
	private String account_name;	//买家名称
	private BigInteger com_id;		//卖家标识
	private String com_name;		//卖家名称
	private String order_state_name;	//订单异动名称
	private String reason;		//备注
	private Timestamp trans_time;	//订单异动时间
	public OrderTransNode() {
		super();
	}
	public OrderTransNode(BigInteger trans_id, BigInteger order_id, BigInteger account_id, String account_name,
			BigInteger com_id, String com_name, String order_state_name, String reason, Timestamp trans_time) {
		super();
		this.trans_id = trans_id;
		this.order_id = order_id;
		this.account_id = account_id;
		this.account_name = account_name;
		this.com_id = com_id;
		this.com_name = com_name;
		this.order_state_name = order_state_name;
		this.reason = reason;
		this.trans_time = trans_time;
	}
	public BigInteger getTrans_id() {
		return trans_id;
	}
	public void setTrans_id(BigInteger trans_id) {
		this.trans_id = trans_id;
	}
	public BigInteger getOrder_id() {
		return order_id;
	}
	public void setOrder_id(BigInteger order_id) {
		this.order_id = order_id;
	}
	public BigInteger getAccount_id() {
		return account_id;
	}
	public void setAccount_id(BigInteger account_id) {
		this.account_id = account_id;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public BigInteger getCom_id() {
		return com_id;
	}
	public void setCom_id(BigInteger com_id) {
		this.com_id = com_id;
	}
	public String getCom_name() {
		return com_name;
	}
	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}
	public String getOrder_state_name() {
		return order_state_name;
	}
	public void setOrder_state_name(String order_state_name) {
		this.order_state_name = order_state_name;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Timestamp getTrans_time() {
		return trans_time;
	}
	public void setTrans_time(Timestamp trans_time) {
		this.trans_time = trans_time;
	}
}
