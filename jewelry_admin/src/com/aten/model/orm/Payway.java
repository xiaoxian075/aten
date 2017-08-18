/*
 * 系统名称:艾腾云返酒店平台
 * (C)  Copyright aten  2017-05-12 17:03:34  Corporation 
 * All rights reserved.
 * Package:com.aten.model.orm
 * FileName: Payway.java 
 * Author:linjunqin
 */
package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

/**
 * @author linjunqin
 * @Function 支付方式管理  model
 * @date 2017-05-12 17:03:34
 */
public class Payway implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String pay_id;
	private String pay_name;
	private String pay_key;
	private String pay_secret;
	private String pay_img;
	private String pay_url;
	private String is_ios_pay;
	private String is_android_pay;
	private String is_web_pay;
	private String sort_no;
	
	public String getSort_no() {
		return sort_no;
	}
	public void setSort_no(String sort_no) {
		this.sort_no = sort_no;
	}
	public String getPay_id() {
		return pay_id;
	}
	public void setPay_id(String pay_id) {
		this.pay_id = pay_id;
	}
	
	public String getPay_name() {
		return pay_name;
	}
	public void setPay_name(String pay_name) {
		this.pay_name = pay_name;
	}
	
	public String getPay_key() {
		return pay_key;
	}
	public void setPay_key(String pay_key) {
		this.pay_key = pay_key;
	}
	
	public String getPay_secret() {
		return pay_secret;
	}
	public void setPay_secret(String pay_secret) {
		this.pay_secret = pay_secret;
	}
	
	public String getPay_img() {
		return pay_img;
	}
	public void setPay_img(String pay_img) {
		this.pay_img = pay_img;
	}
	
	public String getPay_url() {
		return pay_url;
	}
	public void setPay_url(String pay_url) {
		this.pay_url = pay_url;
	}
	
	public String getIs_ios_pay() {
		return is_ios_pay;
	}
	public void setIs_ios_pay(String is_ios_pay) {
		this.is_ios_pay = is_ios_pay;
	}
	
	public String getIs_android_pay() {
		return is_android_pay;
	}
	public void setIs_android_pay(String is_android_pay) {
		this.is_android_pay = is_android_pay;
	}
	
	public String getIs_web_pay() {
		return is_web_pay;
	}
	public void setIs_web_pay(String is_web_pay) {
		this.is_web_pay = is_web_pay;
	}
	
	
}

