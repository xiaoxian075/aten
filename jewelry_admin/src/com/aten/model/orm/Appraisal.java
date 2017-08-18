package com.aten.model.orm;

import java.io.Serializable;
import java.util.Date;

public class Appraisal implements Serializable {

	private static final long serialVersionUID = 1L;

	private String appraisal_id;
	private String appraisal_name;
	private String the_area;
	private String appraisal_contacts;
	private String contacts_way;
	private String sort_no;
	private String state;
	private String create_time;
	private String note;

	public String getAppraisal_id() {
		return appraisal_id;
	}

	public void setAppraisal_id(String appraisal_id) {
		this.appraisal_id = appraisal_id;
	}

	public String getAppraisal_name() {
		return appraisal_name;
	}

	public void setAppraisal_name(String appraisal_name) {
		this.appraisal_name = appraisal_name;
	}

	public String getThe_area() {
		return the_area;
	}

	public void setThe_area(String the_area) {
		this.the_area = the_area;
	}

	public String getAppraisal_contacts() {
		return appraisal_contacts;
	}

	public void setAppraisal_contacts(String appraisal_contacts) {
		this.appraisal_contacts = appraisal_contacts;
	}

	public String getContacts_way() {
		return contacts_way;
	}

	public void setContacts_way(String contacts_way) {
		this.contacts_way = contacts_way;
	}

	public String getSort_no() {
		return sort_no;
	}

	public void setSort_no(String sort_no) {
		this.sort_no = sort_no;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
