package com.aten.model.orm;


import com.communal.util.StringUtil;

import java.io.Serializable;
import java.util.Date;

public class AccountWithdrawBill implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String create_time;
	private String account_id;
	private String amount;
	private String withdraw_note;
	private String audit_state;
	private String audit_time;
	private String audit_note;
	private String card_no;
	private String card_no_name;
	private String opening_bank;
	private String device_code;
	private String ip;
	private String lng;
	private String lat;
	private String audit_men_name;
	private String audit_men_role;
	private String login_name;
	private String total_amount;

	public String getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getAudit_men_name() {
		return audit_men_name;
	}

	public void setAudit_men_name(String audit_men_name) {
		this.audit_men_name = audit_men_name;
	}

	public String getAudit_men_role() {
		return audit_men_role;
	}

	public void setAudit_men_role(String audit_men_role) {
		this.audit_men_role = audit_men_role;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCreate_time() {
		return StringUtil.getStandDate(create_time);
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
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getWithdraw_note() {
		return withdraw_note;
	}
	public void setWithdraw_note(String withdraw_note) {
		this.withdraw_note = withdraw_note;
	}
	
	public String getAudit_state() {
		return audit_state;
	}
	public void setAudit_state(String audit_state) {
		this.audit_state = audit_state;
	}
	
	public String getAudit_time() {
		return StringUtil.getStandDate(audit_time);
	}
	public void setAudit_time(String audit_time) {
		this.audit_time = audit_time;
	}
	
	public String getAudit_note() {
		return audit_note;
	}
	public void setAudit_note(String audit_note) {
		this.audit_note = audit_note;
	}
	
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	
	public String getCard_no_name() {
		return card_no_name;
	}
	public void setCard_no_name(String card_no_name) {
		this.card_no_name = card_no_name;
	}
	
	public String getOpening_bank() {
		return opening_bank;
	}
	public void setOpening_bank(String opening_bank) {
		this.opening_bank = opening_bank;
	}
	
	public String getDevice_code() {
		return device_code;
	}
	public void setDevice_code(String device_code) {
		this.device_code = device_code;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	
	
}

