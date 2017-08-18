package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class Identify implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String iden_id;
	private String iden_number;
	private String iden_name;
	private String iden_province;
	private String iden_city;
	private String iden_county;
	private String iden_contacts;
	private String iden_contacts_way;
	private String sort;
	private String status;
	private String create_time;
	
	
	public String getIden_id() {
		return iden_id;
	}
	public void setIden_id(String iden_id) {
		this.iden_id = iden_id;
	}
	
	public String getIden_name() {
		return iden_name;
	}
	public void setIden_name(String iden_name) {
		this.iden_name = iden_name;
	}
	
	public String getIden_number() {
		return iden_number;
	}
	public void setIden_number(String iden_number) {
		this.iden_number = iden_number;
	}
	public String getIden_province() {
		return iden_province;
	}
	public void setIden_province(String iden_province) {
		this.iden_province = iden_province;
	}
	
	public String getIden_city() {
		return iden_city;
	}
	public void setIden_city(String iden_city) {
		this.iden_city = iden_city;
	}
	
	public String getIden_county() {
		return iden_county;
	}
	public void setIden_county(String iden_county) {
		this.iden_county = iden_county;
	}
	
	public String getIden_contacts() {
		return iden_contacts;
	}
	public void setIden_contacts(String iden_contacts) {
		this.iden_contacts = iden_contacts;
	}
	
	public String getIden_contacts_way() {
		return iden_contacts_way;
	}
	public void setIden_contacts_way(String iden_contacts_way) {
		this.iden_contacts_way = iden_contacts_way;
	}
	
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
	
}

