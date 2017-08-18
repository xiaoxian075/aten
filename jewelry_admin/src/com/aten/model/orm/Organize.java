/*
 * 系统名称:艾腾云返酒店平台
 * (C)  Copyright aten  2017-05-10 19:21:01  Corporation 
 * All rights reserved.
 * Package:com.aten.model.orm
 * FileName: Organize.java 
 * Author:linjunqin
 */
package com.aten.model.orm;


import com.aten.function.OrgFuc;
import com.communal.util.StringUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * @author linjunqin
 * @Function 部门管理功能  model
 * @date 2017-05-10 19:21:01
 */
public class Organize implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String org_id;
	private String org_name;
	private String parent_org_id;
	private String sort_no;
	private String is_show;
	private String level_org;
	private String note;
	private String parent_org_name;
	private String parent_org_code;
	private String  org_code;

	public String getParent_org_code() {
		return parent_org_code;
	}

	public void setParent_org_code(String parent_org_code) {
		this.parent_org_code = parent_org_code;
	}

	public String getOrg_code() {
		return org_code;
	}

	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}

	public String getParent_org_name() {
		return parent_org_name;
	}

	public void setParent_org_name(String parent_org_name) {
		this.parent_org_name = parent_org_name;
	}

	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	
	public String getParent_org_id() {
		return parent_org_id;
	}
	public void setParent_org_id(String parent_org_id) {
		this.parent_org_id = parent_org_id;
	}
	
	public String getSort_no() {
		return sort_no;
	}
	public void setSort_no(String sort_no) {
		this.sort_no = sort_no;
	}
	
	public String getIs_show() {
		return is_show;
	}
	public void setIs_show(String is_show) {
		this.is_show = is_show;
	}
	
	public String getLevel_org() {
		return level_org;
	}
	public void setLevel_org(String level_org) {
		this.level_org = level_org;
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
}

