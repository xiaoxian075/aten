/*
 * 系统名称:艾腾云返酒店平台
 * (C)  Copyright aten  2017-01-04 12:10:58  Corporation 
 * All rights reserved.
 * Package:com.aten.model.orm
 * FileName: Sysconfig.java 
 * Author:linjunqin
 */
package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

 /**
 * @author linjunqin
 * @Description 系统设置
 * @date 2016-12-13  2017-01-04 12:10:58
 */
public class Sysconfig implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String var_id;
	private String var_name;
	private String var_value;
	private String var_desc;
	private String var_group;
	private String var_type;
	private String sort_no;
		
	
	
	
	public String getVar_id() {
		return var_id;
	}
	public void setVar_id(String var_id) {
		this.var_id = var_id;
	}
	
	public String getVar_name() {
		return var_name;
	}
	public void setVar_name(String var_name) {
		this.var_name = var_name;
	}
	
	public String getVar_value() {
		return var_value;
	}
	public void setVar_value(String var_value) {
		this.var_value = var_value;
	}
	
	public String getVar_desc() {
		return var_desc;
	}
	public void setVar_desc(String var_desc) {
		this.var_desc = var_desc;
	}
	
	public String getVar_group() {
		return var_group;
	}
	public void setVar_group(String var_group) {
		this.var_group = var_group;
	}
	
	public String getVar_type() {
		return var_type;
	}
	public void setVar_type(String var_type) {
		this.var_type = var_type;
	}
	
	public String getSort_no() {
		return sort_no;
	}
	public void setSort_no(String sort_no) {
		this.sort_no = sort_no;
	}
	
		
	
}

