package com.aten.model.orm;


import com.communal.util.StringUtil;

import java.io.Serializable;
import java.util.Date;

public class Coupon implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String coupon_id;
	private String create_time;
	private String last_time;
	private String coupon_type;
	private String coupon_name;
	private String coupon_num;
	private String coupon_amount;
	private String use_amount;
	private String use_num;
	private String use_type;
	private String end_time;
	private String account_get_num;
	private String account_use_num;
	private String state;
	private String[] goodsIds;

	public String[] getGoodsIds() {
		return goodsIds;
	}

	public void setGoodsIds(String[] goodsIds) {
		this.goodsIds = goodsIds;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}
	
	public String getCreate_time() {
		return  StringUtil.getStandDate(create_time);
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
	public String getLast_time() {
		return last_time;
	}
	public void setLast_time(String last_time) {
		this.last_time = last_time;
	}
	
	public String getCoupon_type() {
		return coupon_type;
	}
	public void setCoupon_type(String coupon_type) {
		this.coupon_type = coupon_type;
	}
	
	public String getCoupon_name() {
		return coupon_name;
	}
	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}
	
	public String getCoupon_num() {
		return coupon_num;
	}
	public void setCoupon_num(String coupon_num) {
		this.coupon_num = coupon_num;
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
	
	public String getUse_num() {
		return use_num;
	}
	public void setUse_num(String use_num) {
		this.use_num = use_num;
	}
	
	public String getUse_type() {
		return use_type;
	}
	public void setUse_type(String use_type) {
		this.use_type = use_type;
	}
	
	public String getEnd_time() {
		return StringUtil.getStandDate(end_time);
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	
	public String getAccount_get_num() {
		return account_get_num;
	}
	public void setAccount_get_num(String account_get_num) {
		this.account_get_num = account_get_num;
	}
	
	public String getAccount_use_num() {
		return account_use_num;
	}
	public void setAccount_use_num(String account_use_num) {
		this.account_use_num = account_use_num;
	}
	
	
}

