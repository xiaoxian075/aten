package com.aten.model.orm;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

public class OrderFullbook implements Serializable {
	private static final long serialVersionUID = 505439449932669139L;
	
	private BigInteger order_id;
	private int full_send_time_type;	//全额预售时间类型
	private String full_send_time_type_name;	//全额预售时间类型名称
	private Timestamp end_book_time;	//预售结束时间
	private Timestamp end_send_time;	//最后发货时间
	private Timestamp create_time;	//创建时间
	public OrderFullbook() {
		super();
	}
	public OrderFullbook(BigInteger order_id, int full_send_time_type, String full_send_time_type_name,
			Timestamp end_book_time, Timestamp end_send_time, Timestamp create_time) {
		super();
		this.order_id = order_id;
		this.full_send_time_type = full_send_time_type;
		this.full_send_time_type_name = full_send_time_type_name;
		this.end_book_time = end_book_time;
		this.end_send_time = end_send_time;
		this.create_time = create_time;
	}
	public BigInteger getOrder_id() {
		return order_id;
	}
	public void setOrder_id(BigInteger order_id) {
		this.order_id = order_id;
	}
	public int getFull_send_time_type() {
		return full_send_time_type;
	}
	public void setFull_send_time_type(int full_send_time_type) {
		this.full_send_time_type = full_send_time_type;
	}
	public String getFull_send_time_type_name() {
		return full_send_time_type_name;
	}
	public void setFull_send_time_type_name(String full_send_time_type_name) {
		this.full_send_time_type_name = full_send_time_type_name;
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
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
}
