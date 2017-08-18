/*
 * 系统名称:艾腾云返酒店平台
 * (C)  Copyright aten  Tue Jan 03 17:28:02 CST 2017  Corporation 
 * All rights reserved.
 * Package:com.aten.model.orm
 * FileName: Area.java 
 * Author:linjunqin
 */
package com.aten.model.orm;


import com.aten.function.AreaFuc;
import com.communal.util.StringUtil;

import java.io.Serializable;
import java.util.Date;

 /**
 * @author linjunqin
 * @Description 系统地区管理
 * @date 2016-12-13  Tue Jan 03 17:28:02 CST 2017
 */
public class Area implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String area_id;
	private String area_name;
	private String en_name;//地区拼音
	private String word_index;//拼音首字母大写
	private String parent_area_id;
	private String sort_no;//0
	private String area_level;//lev+1
	private String is_city;//0
	private String region;
	private String level_area;
	private String parent_area_name;
    private String area_level_name;
    private String state;//1
	private String xz_code;
	private String post_code;

	
	 public String getXz_code() {
		 return xz_code;
	 }

	 public void setXz_code(String xz_code) {
		 this.xz_code = xz_code;
	 }

	 public String getPost_code() {
		 return post_code;
	 }

	 public void setPost_code(String post_code) {
		 this.post_code = post_code;
	 }

	 public String getState() {
		 return state;
	 }

	 public void setState(String state) {
		 this.state = state;
	 }

	 public String getArea_level_name() {
		 return area_level_name;
	 }

	 public void setArea_level_name(String area_level_name) {
		 this.area_level_name = area_level_name;
	 }

	 public String getParent_area_name() {
		 return parent_area_name;
	 }

	 public void setParent_area_name(String parent_area_name) {
	 	if(!StringUtil.isEmpty(parent_area_name)){
			parent_area_name = AreaFuc.getAreaName(parent_area_name);
		}
		 this.parent_area_name = parent_area_name;
	 }

	 public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getArea_id() {
		return area_id;
	}
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}
	
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	
	public String getEn_name() {
		return en_name;
	}
	public void setEn_name(String en_name) {
		this.en_name = en_name;
	}
	
	public String getWord_index() {
		return word_index;
	}
	public void setWord_index(String word_index) {
		this.word_index = word_index;
	}
	
	public String getParent_area_id() {
		return parent_area_id;
	}
	public void setParent_area_id(String parent_area_id) {
		this.parent_area_id = parent_area_id;
	}
	
	public String getSort_no() {
		return sort_no;
	}
	public void setSort_no(String sort_no) {
		this.sort_no = sort_no;
	}
	
	public String getArea_level() {
		return area_level;
	}
	public void setArea_level(String area_level) {
		this.area_level = area_level;
	}
	
		
	public String getIs_city() {
		return is_city;
	}
	public void setIs_city(String is_city) {
		this.is_city = is_city;
	}

	public String getLevel_area() {
		return level_area;
	}
	public void setLevel_area(String level_area) {
		this.level_area = level_area;
	}
	
	
}

