/*
 * 系统名称:艾腾云返酒店平台
 * (C)  Copyright aten  2017-02-14 10:06:24  Corporation 
 * All rights reserved.
 * Package:com.aten.model.orm
 * FileName: Nav.java 
 * Author:linjunqin
 */
package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

/**
 * @author linjunqin
 * @Function 平台导航管理  model
 * @date 2017-02-14 10:06:24
 */
public class Nav implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nav_name;
	private String nav_ico;
	private String nav_id;
	private String nav_post;
	private String link_url;
	private String state;
	private String sort_no;
	
	
	public String getNav_name() {
		return nav_name;
	}
	public void setNav_name(String nav_name) {
		this.nav_name = nav_name;
	}
	
	public String getNav_ico() {
		return nav_ico;
	}
	public void setNav_ico(String nav_ico) {
		this.nav_ico = nav_ico;
	}
	
	public String getNav_id() {
		return nav_id;
	}
	public void setNav_id(String nav_id) {
		this.nav_id = nav_id;
	}
	
	public String getNav_post() {
		return nav_post;
	}
	public void setNav_post(String nav_post) {
		this.nav_post = nav_post;
	}
	
	public String getLink_url() {
		return link_url;
	}
	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getSort_no() {
		return sort_no;
	}
	public void setSort_no(String sort_no) {
		this.sort_no = sort_no;
	}
	
	
}

