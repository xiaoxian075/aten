/*
 * 系统名称:艾腾云返酒店平台
 * (C)  Copyright aten  ${ydt}  Corporation 
 * All rights reserved.
 * Package:com.aten.model.orm
 * FileName: Sysmenu.java 
 * Author:linjunqin
 */
package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;

 /**
 * @author linjunqin
 * @Description 系统菜单管理
 * @date 2016-12-13  10:06:24
 */
public class Sysmenu implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String menu_id;
	private String menu_name;
	private String parent_menu_id;
	private String menu_level;
	private String sort_no;
	private String is_show;
	private String menu_url;
	private String icon;
	private String target;
	private String level_menu;
	private String note;
	private String plat_role;
	
	
	
	public String getPlat_role() {
		return plat_role;
	}
	public void setPlat_role(String plat_role) {
		this.plat_role = plat_role;
	}
	public String getIs_show() {
		return is_show;
	}
	public void setIs_show(String is_show) {
		this.is_show = is_show;
	}
	public String getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
	
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	
	public String getParent_menu_id() {
		return parent_menu_id;
	}
	public void setParent_menu_id(String parent_menu_id) {
		this.parent_menu_id = parent_menu_id;
	}
	
	public String getMenu_level() {
		return menu_level;
	}
	public void setMenu_level(String menu_level) {
		this.menu_level = menu_level;
	}
	
	public String getSort_no() {
		return sort_no;
	}
	public void setSort_no(String sort_no) {
		this.sort_no = sort_no;
	}
	
	public String getMenu_url() {
		return menu_url;
	}
	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}
		
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getLevel_menu() {
		return level_menu;
	}
	public void setLevel_menu(String level_menu) {
		this.level_menu = level_menu;
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
}

