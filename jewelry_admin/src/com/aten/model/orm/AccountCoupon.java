package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class AccountCoupon implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String create_time;
	private String account_id;
	private String coupon_id;
	private String coupon_name;
	private String coupon_amount;
	private String use_amount;
	private String use_type;
	private String end_time;
	private String state;
	private  String is_del;
	private String use_time;

	public String getUse_time() {
		return use_time;
	}

	public void setUse_time(String use_time) {
		this.use_time = use_time;
	}

	public String getIs_del() {
		return is_del;
	}

	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	
	public String getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}
	
	public String getCoupon_name() {
		return coupon_name;
	}
	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}
	
	public String getCoupon_amount() {
		return coupon_amount;
	}
	public void setCoupon_amount(String coupon_amount) {
		this.coupon_amount = coupon_amount;
	}
	
	public String getUse_amount() {
		return use_amount;
	}
	public void setUse_amount(String use_amount) {
		this.use_amount = use_amount;
	}
	
	public String getUse_type() {
		return use_type;
	}
	public void setUse_type(String use_type) {
		this.use_type = use_type;
	}
	
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}

