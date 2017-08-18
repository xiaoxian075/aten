package com.aten.model.orm;


import com.communal.util.StringUtil;

import java.io.Serializable;
import java.util.Date;

public class Divided implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String divided_id;
	private String divided_date;
	private String order_number;
	private String order_time;
	private String account_id;
	private String login_name;
	private String account_level;
	private String order_amount;
	private String divided_amount;
	private String create_time;
	private String start_date;
	private String end_date;

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getDivided_id() {
		return divided_id;
	}

	public void setDivided_id(String divided_id) {
		this.divided_id = divided_id;
	}

	public String getDivided_date() {
		return divided_date;
	}

	public void setDivided_date(String divided_date) {
		this.divided_date = divided_date;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getOrder_time() {
		return StringUtil.getStandDate(order_time);
	}

	public void setOrder_time(String order_time) {
		this.order_time = order_time;
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

	public String getAccount_level() {
		return account_level;
	}

	public void setAccount_level(String account_level) {
		this.account_level = account_level;
	}

	public String getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}

	public String getDivided_amount() {
		return divided_amount;
	}

	public void setDivided_amount(String divided_amount) {
		this.divided_amount = divided_amount;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
}

