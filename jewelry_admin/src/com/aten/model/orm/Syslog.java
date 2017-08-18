/*
 * 系统名称:艾腾云返酒店平台
 * (C)  Copyright aten  2017-01-06 09:57:06  Corporation 
 * All rights reserved.
 * Package:com.aten.model.orm
 * FileName: Syslog.java 
 * Author:linjunqin
 */
package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

 /**
 * @author linjunqin
 * @Description 系统日志
 * @date 2016-12-13  2017-01-06 09:57:06
 */
public class Syslog implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String syslog_id;
	private String back_id;
	private String back_name;
	private String back_type;
	private String method;
	private String content;
	private String error_code;
		
	private String paras;
	private String error_content;
	private String sys_layer;
	private String ip;
	private String in_date;
	
	
	
	public String getSyslog_id() {
		return syslog_id;
	}
	public void setSyslog_id(String syslog_id) {
		this.syslog_id = syslog_id;
	}
	
	public String getBack_id() {
		return back_id;
	}
	public void setBack_id(String back_id) {
		this.back_id = back_id;
	}
	
	public String getBack_name() {
		return back_name;
	}
	public void setBack_name(String back_name) {
		this.back_name = back_name;
	}
	
	public String getBack_type() {
		return back_type;
	}
	public void setBack_type(String back_type) {
		this.back_type = back_type;
	}
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	
		
	public String getParas() {
		return paras;
	}
	public void setParas(String paras) {
		this.paras = paras;
	}
	
	public String getError_content() {
		return error_content;
	}
	public void setError_content(String error_content) {
		this.error_content = error_content;
	}
	
	public String getSys_layer() {
		return sys_layer;
	}
	public void setSys_layer(String sys_layer) {
		this.sys_layer = sys_layer;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getIn_date() {
		return in_date;
	}
	public void setIn_date(String in_date) {
		if(in_date.indexOf(".0")>-1)
			in_date = in_date.replace(".0", "");
		this.in_date = in_date;
	}
	
	
}

