/*
 * 系统名称:艾腾云返酒店平台
 * (C)  Copyright aten  2017-05-04 10:31:57  Corporation 
 * All rights reserved.
 * Package:com.aten.model.orm
 * FileName: AttrValue.java 
 * Author:linjunqin
 */
package com.aten.model.orm;


import java.io.Serializable;

/**
 * @author linjunqin
 * @Function 属性值管理  model
 * @date 2017-05-04 10:31:57
 */
public class AttrValue implements Serializable {
	private static final long serialVersionUID = -2284179040411406488L;
	private String attr_value_ico;
	private String sort_no;
	private String attr_value_id;
	private String attr_id;
	private String attr_value;
	private String attr_name;
	private String state;


	public AttrValue() {
		super();
		this.attr_value_ico = "";
		this.sort_no = "";
		this.attr_value_id = "";
		this.attr_id = "";
		this.attr_value = "";
		this.attr_name = "";
		this.state = "";
	}

	public AttrValue(String attr_value_ico, String sort_no, String attr_value_id, String attr_id, String attr_value,
			String attr_name, String state) {
		super();
		this.attr_value_ico = attr_value_ico;
		this.sort_no = sort_no;
		this.attr_value_id = attr_value_id;
		this.attr_id = attr_id;
		this.attr_value = attr_value;
		this.attr_name = attr_name;
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAttr_name() {
		return attr_name;
	}
	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}
	
	
	public String getAttr_value_ico() {
		return attr_value_ico;
	}
	public void setAttr_value_ico(String attr_value_ico) {
		this.attr_value_ico = attr_value_ico;
	}
	
	public String getSort_no() {
		return sort_no;
	}
	public void setSort_no(String sort_no) {
		this.sort_no = sort_no;
	}
	
	public String getAttr_value_id() {
		return attr_value_id;
	}
	public void setAttr_value_id(String attr_value_id) {
		this.attr_value_id = attr_value_id;
	}
	
	public String getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
	}
	
	public String getAttr_value() {
		return attr_value;
	}
	public void setAttr_value(String attr_value) {
		this.attr_value = attr_value;
	}
	
	
}

