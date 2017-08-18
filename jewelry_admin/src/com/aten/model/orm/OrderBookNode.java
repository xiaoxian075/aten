package com.aten.model.orm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

public class OrderBookNode implements Serializable{
	private static final long serialVersionUID = 2702298445239070099L;
	
	private BigInteger order_id;	//订单ID
	private int model;				//预售模式 1 全额    2 定金
	private BigDecimal book_amount;	//预售金额
	private BigDecimal return_amount;	//尾款金额
	private Timestamp end_book_time;	//预售结束时间
	private Timestamp end_send_time;	//最后发货时间
	private Timestamp ebs_time;			//尾款支付开始时间
	private Timestamp create_time;		//创建时间
	public BigInteger getOrder_id() {
		return order_id;
	}
	public void setOrder_id(BigInteger order_id) {
		this.order_id = order_id;
	}
	public int getModel() {
		return model;
	}
	public void setModel(int model) {
		this.model = model;
	}
	public BigDecimal getBook_amount() {
		return book_amount;
	}
	public void setBook_amount(BigDecimal book_amount) {
		this.book_amount = book_amount;
	}
	public BigDecimal getReturn_amount() {
		return return_amount;
	}
	public void setReturn_amount(BigDecimal return_amount) {
		this.return_amount = return_amount;
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
	public Timestamp getEbs_time() {
		return ebs_time;
	}
	public void setEbs_time(Timestamp ebs_time) {
		this.ebs_time = ebs_time;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
}
