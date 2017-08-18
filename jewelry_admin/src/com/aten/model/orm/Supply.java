package com.aten.model.orm;

import java.io.Serializable;
import java.util.Date;

public class Supply implements Serializable {

	private static final long serialVersionUID = 1L;

	private String supply_id;
	private String supply_name;
	private String the_area;
	private String supply_contacts;
	private String supply_contacts_phone;
	private String sort_no;
	private String state;
	private String valid_time_start;
	private String valid_time_end;
	private String license_number;
	private String license_picture;
	private String legal_name;
	private String legal_id_card_number;
	private String legal_id_card_picture;
	private String create_time;
	private String note;

	public String getSupply_id() {
		return supply_id;
	}

	public void setSupply_id(String supply_id) {
		this.supply_id = supply_id;
	}

	public String getSupply_name() {
		return supply_name;
	}

	public void setSupply_name(String supply_name) {
		this.supply_name = supply_name;
	}

	public String getThe_area() {
		return the_area;
	}

	public void setThe_area(String the_area) {
		this.the_area = the_area;
	}

	public String getSupply_contacts() {
		return supply_contacts;
	}

	public void setSupply_contacts(String supply_contacts) {
		this.supply_contacts = supply_contacts;
	}

	public String getSupply_contacts_phone() {
		return supply_contacts_phone;
	}

	public void setSupply_contacts_phone(String supply_contacts_phone) {
		this.supply_contacts_phone = supply_contacts_phone;
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

	public String getValid_time_start() {
		return valid_time_start;
	}

	public void setValid_time_start(String valid_time_start) {
		this.valid_time_start = valid_time_start;
	}

	public String getValid_time_end() {
		return valid_time_end;
	}

	public void setValid_time_end(String valid_time_end) {
		this.valid_time_end = valid_time_end;
	}

	public String getLicense_number() {
		return license_number;
	}

	public void setLicense_number(String license_number) {
		this.license_number = license_number;
	}

	public String getLicense_picture() {
		return license_picture;
	}

	public void setLicense_picture(String license_picture) {
		this.license_picture = license_picture;
	}

	public String getLegal_name() {
		return legal_name;
	}

	public void setLegal_name(String legal_name) {
		this.legal_name = legal_name;
	}

	public String getLegal_id_card_number() {
		return legal_id_card_number;
	}

	public void setLegal_id_card_number(String legal_id_card_number) {
		this.legal_id_card_number = legal_id_card_number;
	}

	public String getLegal_id_card_picture() {
		return legal_id_card_picture;
	}

	public void setLegal_id_card_picture(String legal_id_card_picture) {
		this.legal_id_card_picture = legal_id_card_picture;
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
