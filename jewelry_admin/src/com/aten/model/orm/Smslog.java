/*
 * 系统名称:艾腾云返酒店平台
 * (C)  Copyright aten  2017-01-06 10:08:44  Corporation 
 * All rights reserved.
 * Package:com.aten.model.orm
 * FileName: Smslog.java 
 * Author:linjunqin
 */
package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

 /**
 * @author linjunqin
 * @Description 短信日志
 * @date 2016-12-13  2017-01-06 10:08:44
 */
public class Smslog implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String sms_id;
	private String sms_mobile;
	private String sms_msg;
	private String sms_type;
	private String send_time;
	private String result_content;
	private String sms_code;
	private String state;
	private String is_send;
	private String para_name;
	
	
	public String getPara_name() {
		return para_name;
	}
	public void setPara_name(String para_name) {
		this.para_name = para_name;
	}
	public String getSms_id() {
		return sms_id;
	}
	public void setSms_id(String sms_id) {
		this.sms_id = sms_id;
	}
	
	public String getSms_mobile() {
		return sms_mobile;
	}
	public void setSms_mobile(String sms_mobile) {
		this.sms_mobile = sms_mobile;
	}
	
	public String getSms_msg() {
		return sms_msg;
	}
	public void setSms_msg(String sms_msg) {
		this.sms_msg = sms_msg;
	}
	
	public String getSms_type() {
		return sms_type;
	}
	public void setSms_type(String sms_type) {
		this.sms_type = sms_type;
	}
	
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		if(send_time.indexOf(".0")>-1)
			send_time = send_time.replace(".0", "");
		this.send_time = send_time;
	}
	
	public String getResult_content() {
		return result_content;
	}
	public void setResult_content(String result_content) {
		this.result_content = result_content;
	}
	
	public String getSms_code() {
		return sms_code;
	}
	public void setSms_code(String sms_code) {
		this.sms_code = sms_code;
	}
	
		
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getIs_send() {
		return is_send;
	}
	public void setIs_send(String is_send) {
		this.is_send = is_send;
	}
	
	
}

