package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class OrderIntegral implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String ex_id;
	private String order_number;
	private String integral_goods_name;
	private String exchange_time;
	private String use_integral;
	private String account_id;
	private String login_name;
	private String integral_id;

	
	
	
	public String getEx_id() {
		return ex_id;
	}
	public void setEx_id(String ex_id) {
		this.ex_id = ex_id;
	}
	
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	
	public String getIntegral_goods_name() {
		return integral_goods_name;
	}
	public void setIntegral_goods_name(String integral_goods_name) {
		this.integral_goods_name = integral_goods_name;
	}
	
	public String getExchange_time() {
		return exchange_time;
	}
	public void setExchange_time(String exchange_time) {
		this.exchange_time = exchange_time;
	}
	
	public String getUse_integral() {
		return use_integral;
	}
	public void setUse_integral(String use_integral) {
		this.use_integral = use_integral;
	}
	
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	
	public String getIntegral_id() {
		return integral_id;
	}
	public void setIntegral_id(String integral_id) {
		this.integral_id = integral_id;
	}
	
	
}

