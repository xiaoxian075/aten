package com.aten.model.orm;


import com.communal.util.AmountUtil;

import java.io.Serializable;
import java.util.Date;

public class CouponUseCat implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String create_time;
	private String coupon_id;
	private String use_cat_id;
	private String use_cat_name;
	private String fixed_price;
	private String com_id;

	public String getFixed_price() {
		return AmountUtil.formatMoney(fixed_price);
	}

	public void setFixed_price(String fixed_price) {
		this.fixed_price = fixed_price;
	}

	public String getCom_id() {
		return com_id;
	}

	public void setCom_id(String com_id) {
		this.com_id = com_id;
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
	
	public String getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}
	
	public String getUse_cat_id() {
		return use_cat_id;
	}
	public void setUse_cat_id(String use_cat_id) {
		this.use_cat_id = use_cat_id;
	}
	
	public String getUse_cat_name() {
		return use_cat_name;
	}
	public void setUse_cat_name(String use_cat_name) {
		this.use_cat_name = use_cat_name;
	}
	
	
}

