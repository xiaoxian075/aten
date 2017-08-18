package com.aten.model.orm;

import java.io.Serializable;
import java.util.Date;

public class AccountBalanceApprove implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ba_id;
	private String approve_num;
	private String create_time;
	private String submitter_id;
	private String submitter_name;
	private String submitter_rolename;
	private String login_name;
	private String account_id;
	private String io_type;
	private String approve_amount;
	private String submitter_note;
	private String submitter_img;
	private String audit_state;
	private String approve_mana_id;
	private String approve_mana_name;
	private String approve_rolename;
	private String approve_time;
	private String approve_note;

	public String getBa_id() {
		return ba_id;
	}

	public void setBa_id(String ba_id) {
		this.ba_id = ba_id;
	}

	public String getApprove_num() {
		return approve_num;
	}

	public void setApprove_num(String approve_num) {
		this.approve_num = approve_num;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getSubmitter_id() {
		return submitter_id;
	}

	public void setSubmitter_id(String submitter_id) {
		this.submitter_id = submitter_id;
	}

	public String getSubmitter_name() {
		return submitter_name;
	}

	public void setSubmitter_name(String submitter_name) {
		this.submitter_name = submitter_name;
	}

	public String getSubmitter_rolename() {
		return submitter_rolename;
	}

	public void setSubmitter_rolename(String submitter_rolename) {
		this.submitter_rolename = submitter_rolename;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getIo_type() {
		return io_type;
	}

	public void setIo_type(String io_type) {
		this.io_type = io_type;
	}

	public String getApprove_amount() {
		return approve_amount;
	}

	public void setApprove_amount(String approve_amount) {
		this.approve_amount = approve_amount;
	}

	public String getSubmitter_note() {
		return submitter_note;
	}

	public void setSubmitter_note(String submitter_note) {
		this.submitter_note = submitter_note;
	}

	public String getSubmitter_img() {
		return submitter_img;
	}

	public void setSubmitter_img(String submitter_img) {
		this.submitter_img = submitter_img;
	}

	public String getAudit_state() {
		return audit_state;
	}

	public void setAudit_state(String audit_state) {
		this.audit_state = audit_state;
	}

	public String getApprove_mana_id() {
		return approve_mana_id;
	}

	public void setApprove_mana_id(String approve_mana_id) {
		this.approve_mana_id = approve_mana_id;
	}

	public String getApprove_mana_name() {
		return approve_mana_name;
	}

	public void setApprove_mana_name(String approve_mana_name) {
		this.approve_mana_name = approve_mana_name;
	}

	public String getApprove_rolename() {
		return approve_rolename;
	}

	public void setApprove_rolename(String approve_rolename) {
		this.approve_rolename = approve_rolename;
	}

	public String getApprove_time() {
		return approve_time;
	}

	public void setApprove_time(String approve_time) {
		this.approve_time = approve_time;
	}

	public String getApprove_note() {
		return approve_note;
	}

	public void setApprove_note(String approve_note) {
		this.approve_note = approve_note;
	}

}
