/*
 * 系统名称:艾腾云返酒店平台
 * (C)  Copyright aten  2017-03-03 10:50:46  Corporation 
 * All rights reserved.
 * Package:com.aten.model.orm
 * FileName: SmsTemporary.java 
 * Author:linjunqin
 */
package com.aten.model.orm;


import java.io.Serializable;

/**
 * @author linjunqin
 * @Function 临时短信类  model
 * @date 2017-03-03 10:50:46
 */
public class SmsTemporary implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String temporary_id;
	private String mobile;
	private String sms_code;
	private String type;
	private String in_date;
	
	
	public String getTemporary_id() {
		return temporary_id;
	}
	public void setTemporary_id(String temporary_id) {
		this.temporary_id = temporary_id;
	}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSms_code() {
		return sms_code;
	}
	public void setSms_code(String sms_code) {
		this.sms_code = sms_code;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIn_date() {
		return in_date;
	}
	public void setIn_date(String in_date) {
		this.in_date = in_date;
	}
	
}

