package com.aten.model.orm;


import java.io.Serializable;

public class GoodsCustomAttr implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String custom_attr_id;
	private String custom_alias;
	private String goods_id;
	private String attr_id;
	private String custom_attr_value;
	private String attr_value_id;
	private String attr_type;
	

	
	public String getAttr_type() {
		return attr_type;
	}
	public void setAttr_type(String attr_type) {
		this.attr_type = attr_type;
	}
	public String getCustom_attr_value() {
		return custom_attr_value;
	}
	public void setCustom_attr_value(String custom_attr_value) {
		this.custom_attr_value = custom_attr_value;
	}
	public String getAttr_value_id() {
		return attr_value_id;
	}
	public void setAttr_value_id(String attr_value_id) {
		this.attr_value_id = attr_value_id;
	}
	public String getCustom_attr_id() {
		return custom_attr_id;
	}
	public void setCustom_attr_id(String custom_attr_id) {
		this.custom_attr_id = custom_attr_id;
	}
	
	public String getCustom_alias() {
		return custom_alias;
	}
	public void setCustom_alias(String custom_alias) {
		this.custom_alias = custom_alias;
	}
	
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	
	public String getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
	}
	
	
}

