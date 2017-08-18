package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class AccountIntegralLog implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String create_time;
	private String account_id;
	private String integral;
	private String type;
	private String state;
	private String io_type;

	
	
	
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
	
	public String getIntegral() {
		return integral;
	}
	public void setIntegral(String integral) {
		this.integral = integral;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getIo_type() {
		return io_type;
	}
	public void setIo_type(String io_type) {
		this.io_type = io_type;
	}
	
	
}

