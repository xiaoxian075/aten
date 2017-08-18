package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

/**
 * @author linjunqin
 * @Function 自定义属性功能  model
 * @date 2017-04-28 17:04:01
 */
public class CustomAttr implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String custom_value_id;
	private String attr_id;
	private String attr_value_id;
	private String custom_attr_value;
	private String attr_type;
	private String quote_id;
	
	
	public String getCustom_value_id() {
		return custom_value_id;
	}
	public void setCustom_value_id(String custom_value_id) {
		this.custom_value_id = custom_value_id;
	}
	
	public String getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
	}
	
	public String getAttr_value_id() {
		return attr_value_id;
	}
	public void setAttr_value_id(String attr_value_id) {
		this.attr_value_id = attr_value_id;
	}
	
	public String getCustom_attr_value() {
		return custom_attr_value;
	}
	public void setCustom_attr_value(String custom_attr_value) {
		this.custom_attr_value = custom_attr_value;
	}
	
	public String getAttr_type() {
		return attr_type;
	}
	public void setAttr_type(String attr_type) {
		this.attr_type = attr_type;
	}
	
	public String getQuote_id() {
		return quote_id;
	}
	public void setQuote_id(String quote_id) {
		this.quote_id = quote_id;
	}
	
	
}

