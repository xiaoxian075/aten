/*
 * 系统名称:艾腾云返酒店平台
 * (C)  Copyright aten  2017-01-05 15:27:19  Corporation 
 * All rights reserved.
 * Package:com.aten.model.orm
 * FileName: Manager.java 
 * Author:linjunqin
 */
package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

 /**
 * @author linjunqin
 * @Description 商户
 * @date 2016-12-13  2017-01-05 15:27:19
 */
public class Manager implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String mana_id;
	private String mana_name;
	private String password;
	private String role_code;
	private String phone;
	private String email;
	private String note;
	private String last_login_time;
	private String state;
	private String create_time;
	private String mana_type;
	private String role_name;
	private String ease_id;
	private String ease_pwd;
	private String header_img;
	private String nike_name;
	private String the_org;
	private String real_name;
	private String qq;
	private String sex;
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getThe_org() {
		return the_org;
	}
	public void setThe_org(String the_org) {
		this.the_org = the_org;
	}
	public String getNike_name() {
		return nike_name;
	}
	public void setNike_name(String nike_name) {
		this.nike_name = nike_name;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getMana_id() {
		return mana_id;
	}
	public void setMana_id(String mana_id) {
		this.mana_id = mana_id;
	}
	
	public String getMana_name() {
		return mana_name;
	}
	public void setMana_name(String mana_name) {
		this.mana_name = mana_name;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole_code() {
		return role_code;
	}
	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
		
	public String getLast_login_time() {
		return last_login_time;
	}
	public void setLast_login_time(String last_login_time) {
		if(last_login_time!=null && last_login_time.indexOf(".0")>-1)
			last_login_time = last_login_time.replace(".0", "");
		this.last_login_time = last_login_time;
	}
	
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		if(create_time.indexOf(".0")>-1)
			create_time = create_time.replace(".0", "");
		this.create_time = create_time;
	}
	
	public String getMana_type() {
		return mana_type;
	}
	public void setMana_type(String mana_type) {
		this.mana_type = mana_type;
	}
	public String getEase_id() {
		return ease_id;
	}
	public void setEase_id(String ease_id) {
		this.ease_id = ease_id;
	}
	public String getEase_pwd() {
		return ease_pwd;
	}
	public void setEase_pwd(String ease_pwd) {
		this.ease_pwd = ease_pwd;
	}
	public String getHeader_img() {
		return header_img;
	}
	public void setHeader_img(String header_img) {
		this.header_img = header_img;
	}
	
	
}

