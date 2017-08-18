package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class Sysmsg implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String sysmsg_id;
	private String account_id;
	private String sysmsg_title;
	private String introduction;
	private String sysmsg_content;
	private String in_date;
	private String is_read;
	private String skip_type;
	private String relation_id;

	
	
	
	public String getSysmsg_id() {
		return sysmsg_id;
	}
	public void setSysmsg_id(String sysmsg_id) {
		this.sysmsg_id = sysmsg_id;
	}
	
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	
	public String getSysmsg_title() {
		return sysmsg_title;
	}
	public void setSysmsg_title(String sysmsg_title) {
		this.sysmsg_title = sysmsg_title;
	}
	
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	public String getSysmsg_content() {
		return sysmsg_content;
	}
	public void setSysmsg_content(String sysmsg_content) {
		this.sysmsg_content = sysmsg_content;
	}
	
	public String getIn_date() {
		return in_date;
	}
	public void setIn_date(String in_date) {
		this.in_date = in_date;
	}
	
	public String getIs_read() {
		return is_read;
	}
	public void setIs_read(String is_read) {
		this.is_read = is_read;
	}
	
	public String getSkip_type() {
		return skip_type;
	}
	public void setSkip_type(String skip_type) {
		this.skip_type = skip_type;
	}
	
	public String getRelation_id() {
		return relation_id;
	}
	public void setRelation_id(String relation_id) {
		this.relation_id = relation_id;
	}
	
	
}

