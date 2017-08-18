package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class Brand implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String brand_id;
	private String brand_name;
	private String en_name;
	private String word_index;
	private String brand_logo;
	private String sort_no;
	private String brand_story;
	private String state;
	private String is_recom;
	
	public String getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}
	
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	
	public String getEn_name() {
		return en_name;
	}
	public void setEn_name(String en_name) {
		this.en_name = en_name;
	}
	
	public String getWord_index() {
		return word_index;
	}
	public void setWord_index(String word_index) {
		this.word_index = word_index;
	}
	
	public String getBrand_logo() {
		return brand_logo;
	}
	public void setBrand_logo(String brand_logo) {
		this.brand_logo = brand_logo;
	}
	
	public String getSort_no() {
		return sort_no;
	}
	public void setSort_no(String sort_no) {
		this.sort_no = sort_no;
	}
	
	public String getBrand_story() {
		return brand_story;
	}
	public void setBrand_story(String brand_story) {
		this.brand_story = brand_story;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getIs_recom() {
		return is_recom;
	}
	public void setIs_recom(String is_recom) {
		this.is_recom = is_recom;
	}
	
	/*[incremental_add_getset]*/
	
}

