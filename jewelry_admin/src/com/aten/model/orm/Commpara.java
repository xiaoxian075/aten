/*
 * 系统名称:艾腾云返酒店平台
 * (C)  Copyright aten  2017-01-06 10:44:16  Corporation 
 * All rights reserved.
 * Package:com.aten.model.orm
 * FileName: Commpara.java 
 * Author:linjunqin
 */
package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

 /**
 * @author linjunqin
 * @Description 系统参数
 * @date 2016-12-13  2017-01-06 10:44:16
 */
public class Commpara {

	private String para_id;
	private String para_code;
	private String para_name;
	private String para_key;
	private String sort_no;
	private String state;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPara_id() {
		return para_id;
	}
	public void setPara_id(String para_id) {
		this.para_id = para_id;
	}
	
	public String getPara_code() {
		return para_code;
	}
	public void setPara_code(String para_code) {
		this.para_code = para_code;
	}
	
	public String getPara_name() {
		return para_name;
	}
	public void setPara_name(String para_name) {
		this.para_name = para_name;
	}
	
	public String getPara_key() {
		return para_key;
	}
	public void setPara_key(String para_key) {
		this.para_key = para_key;
	}
	
	public String getSort_no() {
		return sort_no;
	}
	public void setSort_no(String sort_no) {
		this.sort_no = sort_no;
	}
		
}

