package com.aten.model.orm;

import java.io.Serializable;

public class GoodsClass implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String class_id;
	private String create_time;
	private String class_name;
	private String sort_no;
	private String f_class_id;
	private String is_show;
	private String brand_id;
	private String attr_id;
	private String standard_id;
	private double earnings;
	private String supply_id;
	private String identify_id;
	private String word_index;
	private String f_class_name;
	
	
	
	public String getF_class_name() {
		return f_class_name;
	}
	public void setF_class_name(String f_class_name) {
		this.f_class_name = f_class_name;
	}
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getSort_no() {
		return sort_no;
	}
	public void setSort_no(String sort_no) {
		this.sort_no = sort_no;
	}
	public String getF_class_id() {
		return f_class_id;
	}
	public void setF_class_id(String f_class_id) {
		this.f_class_id = f_class_id;
	}
	
	public String getIs_show() {
		return is_show;
	}
	public void setIs_show(String is_show) {
		this.is_show = is_show;
	}
	public String getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}
	public String getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
	}
	public String getStandard_id() {
		return standard_id;
	}
	public void setStandard_id(String standard_id) {
		this.standard_id = standard_id;
	}
	public double getEarnings() {
		return earnings;
	}
	public void setEarnings(double earnings) {
		this.earnings = earnings;
	}
	public String getSupply_id() {
		return supply_id;
	}
	public void setSupply_id(String supply_id) {
		this.supply_id = supply_id;
	}
	public String getIdentify_id() {
		return identify_id;
	}
	public void setIdentify_id(String identify_id) {
		this.identify_id = identify_id;
	}
	public String getWord_index() {
		return word_index;
	}
	public void setWord_index(String word_index) {
		this.word_index = word_index;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
