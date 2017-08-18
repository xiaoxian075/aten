package com.aten.model.orm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

public class OrderDeposit implements Serializable {
	private static final long serialVersionUID = -5308551174859938548L;
	
	private BigInteger order_id;
	private BigDecimal deposit_amount;	//订金金额
	private BigDecimal retainage;		//尾款金额
	private Timestamp end_book_time;	//预售结束时间
	private Timestamp end_send_time;	//最后发货时间
	private Timestamp retainage_pay_start_time;	//尾款支付开始时间
	private Timestamp create_time;		//创建时间
	private int pay_state;				//支付状态 0:未付款 1已付订金 2：已付尾款
	private Timestamp deposit_pay_time;	//订金支付时间
	private Timestamp retainage_pay_time;	//尾款支付时间
	private Timestamp retainage_pay_end_time;	//尾款截止时间
	public OrderDeposit() {
		super();
	}
	public OrderDeposit(BigInteger order_id, BigDecimal deposit_amount, BigDecimal retainage, Timestamp end_book_time,
			Timestamp end_send_time, Timestamp retainage_pay_start_time, Timestamp create_time, int pay_state,
			Timestamp deposit_pay_time, Timestamp retainage_pay_time, Timestamp retainage_pay_end_time) {
		super();
		this.order_id = order_id;
		this.deposit_amount = deposit_amount;
		this.retainage = retainage;
		this.end_book_time = end_book_time;
		this.end_send_time = end_send_time;
		this.retainage_pay_start_time = retainage_pay_start_time;
		this.create_time = create_time;
		this.pay_state = pay_state;
		this.deposit_pay_time = deposit_pay_time;
		this.retainage_pay_time = retainage_pay_time;
		this.retainage_pay_end_time = retainage_pay_end_time;
	}
	public BigInteger getOrder_id() {
		return order_id;
	}
	public void setOrder_id(BigInteger order_id) {
		this.order_id = order_id;
	}
	public BigDecimal getDeposit_amount() {
		return deposit_amount;
	}
	public void setDeposit_amount(BigDecimal deposit_amount) {
		this.deposit_amount = deposit_amount;
	}
	public BigDecimal getRetainage() {
		return retainage;
	}
	public void setRetainage(BigDecimal retainage) {
		this.retainage = retainage;
	}
	public Timestamp getEnd_book_time() {
		return end_book_time;
	}
	public void setEnd_book_time(Timestamp end_book_time) {
		this.end_book_time = end_book_time;
	}
	public Timestamp getEnd_send_time() {
		return end_send_time;
	}
	public void setEnd_send_time(Timestamp end_send_time) {
		this.end_send_time = end_send_time;
	}
	public Timestamp getRetainage_pay_start_time() {
		return retainage_pay_start_time;
	}
	public void setRetainage_pay_start_time(Timestamp retainage_pay_start_time) {
		this.retainage_pay_start_time = retainage_pay_start_time;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public int getPay_state() {
		return pay_state;
	}
	public void setPay_state(int pay_state) {
		this.pay_state = pay_state;
	}
	public Timestamp getDeposit_pay_time() {
		return deposit_pay_time;
	}
	public void setDeposit_pay_time(Timestamp deposit_pay_time) {
		this.deposit_pay_time = deposit_pay_time;
	}
	public Timestamp getRetainage_pay_time() {
		return retainage_pay_time;
	}
	public void setRetainage_pay_time(Timestamp retainage_pay_time) {
		this.retainage_pay_time = retainage_pay_time;
	}
	public Timestamp getRetainage_pay_end_time() {
		return retainage_pay_end_time;
	}
	public void setRetainage_pay_end_time(Timestamp retainage_pay_end_time) {
		this.retainage_pay_end_time = retainage_pay_end_time;
	}
}
