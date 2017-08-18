package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class Adv implements Serializable {

	private static final long serialVersionUID = 1L;
	private String adv_id;
	private String adv_code;
	private String adv_name;
	private String adv_introduce;
	private String the_terminal;
	private String adv_type;
	private String is_add_ads;
	private String state;


	public String getAdv_id() {
		return adv_id;
	}

	public void setAdv_id(String adv_id) {
		this.adv_id = adv_id;
	}

	public String getAdv_code() {
		return adv_code;
	}
	public void setAdv_code(String adv_code) {
		this.adv_code = adv_code;
	}
	
	public String getAdv_name() {
		return adv_name;
	}
	public void setAdv_name(String adv_name) {
		this.adv_name = adv_name;
	}
	
	public String getAdv_introduce() {
		return adv_introduce;
	}
	public void setAdv_introduce(String adv_introduce) {
		this.adv_introduce = adv_introduce;
	}
	
	public String getThe_terminal() {
		return the_terminal;
	}
	public void setThe_terminal(String the_terminal) {
		this.the_terminal = the_terminal;
	}
	
	public String getAdv_type() {
		return adv_type;
	}
	public void setAdv_type(String adv_type) {
		this.adv_type = adv_type;
	}
	
	public String getIs_add_ads() {
		return is_add_ads;
	}
	public void setIs_add_ads(String is_add_ads) {
		this.is_add_ads = is_add_ads;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}

