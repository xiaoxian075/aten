/*
 * 系统名称:艾腾云返酒店平台
 * (C)  Copyright aten  2017-01-05 16:51:50  Corporation 
 * All rights reserved.
 * Package:com.aten.model.orm
 * FileName: Power.java 
 * Author:linjunqin
 */
package com.aten.model.orm;

import java.io.Serializable;

/**
 * @author linjunqin
 * @Description 权限
 * @date 2016-12-13 2017-01-05 16:51:50
 */
public class Power implements Serializable {

	private static final long serialVersionUID = 1L;

	private String power_id;
	private String power_name;
	private String menu_name_str;
	private String menu_id;
	private String url;
	private String note;
	private String add_time;
	private String path_name;
	private String plat_role;
	private String is_control_power;

	public String getPlat_role() {
		return plat_role;
	}

	public void setPlat_role(String plat_role) {
		this.plat_role = plat_role;
	}

	public String getPath_name() {
		return path_name;
	}

	public void setPath_name(String path_name) {
		this.path_name = path_name;
	}

	public String getMenu_name_str() {
		return menu_name_str;
	}

	public void setMenu_name_str(String menu_name_str) {
		this.menu_name_str = menu_name_str;
	}

	public String getPower_id() {
		return power_id;
	}

	public void setPower_id(String power_id) {
		this.power_id = power_id;
	}

	public String getPower_name() {
		return power_name;
	}

	public void setPower_name(String power_name) {
		this.power_name = power_name;
	}

	public String getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		if (add_time.indexOf(".0") > -1)
			add_time = add_time.replace(".0", "");
		this.add_time = add_time;
	}

	public String getIs_control_power() {
		return is_control_power;
	}

	public void setIs_control_power(String is_control_power) {
		this.is_control_power = is_control_power;
	}

}
