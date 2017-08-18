package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class Feedback implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String fb_id;
	private String account_id;
	private String nick_name;
	private String fb_content;
	private String fb_time;

	
	
	
	public String getFb_id() {
		return fb_id;
	}
	public void setFb_id(String fb_id) {
		this.fb_id = fb_id;
	}
	
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	
	public String getFb_content() {
		return fb_content;
	}
	public void setFb_content(String fb_content) {
		this.fb_content = fb_content;
	}
	
	public String getFb_time() {
		return fb_time;
	}
	public void setFb_time(String fb_time) {
		this.fb_time = fb_time;
	}
	
	
}

