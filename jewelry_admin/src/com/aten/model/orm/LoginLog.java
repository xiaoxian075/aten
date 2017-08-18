/*
 * 系统名称:艾腾云返酒店平台
 * (C)  Copyright aten  2017-01-05 17:11:20  Corporation 
 * All rights reserved.
 * Package:com.aten.model.orm
 * FileName: Loginlog.java 
 * Author:linjunqin
 */
package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

 /**
 * @author linjunqin
 * @Description 登录日志
 * @date 2016-12-13  2017-01-05 17:11:20
 */
public class LoginLog implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String log_id;
	private String back_id;
	private String back_name;
	private String back_type;
	private String login_ip;
	private String login_time;
	
	
	
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
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
	
	public String getLogin_ip() {
		return login_ip;
	}
	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}
	
	public String getLogin_time() {
		return login_time;
	}
	public void setLogin_time(String login_time) {
		if(login_time.indexOf(".0")>-1)
			login_time = login_time.replace(".0", "");
		this.login_time = login_time;
	}
	
	
}

