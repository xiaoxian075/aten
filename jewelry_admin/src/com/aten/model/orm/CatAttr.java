package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class CatAttr implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String ident_id;
	private String cat_id;
	private String attr_id;
	private String show_type;
	private String option_type;
	private String is_alisa;
	private String is_sku;
	private String is_key;
	private String is_index;
	private String is_must;
	private String is_custom_value;
	private String attr_name;
	private String manual_fee;

	public String getManual_fee() {
		return manual_fee;
	}

	public void setManual_fee(String manual_fee) {
		this.manual_fee = manual_fee;
	}

	public String getAttr_name() {
		return attr_name;
	}

	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}

	public String getIdent_id() {
		return ident_id;
	}
	public void setIdent_id(String ident_id) {
		this.ident_id = ident_id;
	}
	
	public String getCat_id() {
		return cat_id;
	}
	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}
	
	public String getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
	}
	
	public String getShow_type() {
		return show_type;
	}
	public void setShow_type(String show_type) {
		this.show_type = show_type;
	}
	
	public String getOption_type() {
		return option_type;
	}
	public void setOption_type(String option_type) {
		this.option_type = option_type;
	}
	
	public String getIs_alisa() {
		return is_alisa;
	}
	public void setIs_alisa(String is_alisa) {
		this.is_alisa = is_alisa;
	}
	
	public String getIs_sku() {
		return is_sku;
	}
	public void setIs_sku(String is_sku) {
		this.is_sku = is_sku;
	}
	
	public String getIs_key() {
		return is_key;
	}
	public void setIs_key(String is_key) {
		this.is_key = is_key;
	}
	
	public String getIs_index() {
		return is_index;
	}
	public void setIs_index(String is_index) {
		this.is_index = is_index;
	}
	
	public String getIs_must() {
		return is_must;
	}
	public void setIs_must(String is_must) {
		this.is_must = is_must;
	}
	
	public String getIs_custom_value() {
		return is_custom_value;
	}
	public void setIs_custom_value(String is_custom_value) {
		this.is_custom_value = is_custom_value;
	}
	
	
}

