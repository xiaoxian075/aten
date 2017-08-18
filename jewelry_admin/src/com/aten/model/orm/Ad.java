/*
 * 系统名称:艾腾云返酒店平台
 * (C)  Copyright aten  2017-02-14 09:39:53  Corporation 
 * All rights reserved.
 * Package:com.aten.model.orm
 * FileName: Ad.java 
 * Author:linjunqin
 */
package com.aten.model.orm;

import java.io.Serializable;
import java.util.Date;

import com.communal.util.ImageUtil;

/**
 * @author linjunqin
 * @Function 广告类 model
 * @date 2017-02-14 09:39:53
 */
public class Ad implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ad_id;
	private String adv_code;
	private String ad_name;
	private String img_path;
	private String link_url;
	private String start_time;
	private String end_time;
	private String note;
	private String state;
	private String sort_no;
	private String ad_type;
	private String ad_type_name;
	private String the_area;
	private String region;
	private String the_cat;
	private String adv_id;
	private String time_state;

	
	public String getAd_type_name() {
		return ad_type_name;
	}

	public void setAd_type_name(String ad_type_name) {
		this.ad_type_name = ad_type_name;
	}

	public String getAdv_id() {
		return adv_id;
	}

	public void setAdv_id(String adv_id) {
		this.adv_id = adv_id;
	}

	public String getThe_area() {
		return the_area;
	}

	public void setThe_area(String the_area) {
		this.the_area = the_area;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getThe_cat() {
		return the_cat;
	}

	public void setThe_cat(String the_cat) {
		this.the_cat = the_cat;
	}

	public String getAd_type() {
		return ad_type;
	}

	public void setAd_type(String ad_type) {
		this.ad_type = ad_type;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAd_id() {
		return ad_id;
	}

	public void setAd_id(String ad_id) {
		this.ad_id = ad_id;
	}

	public String getTime_state() {
		return time_state;
	}

	public void setTime_state(String time_state) {
		this.time_state = time_state;
	}

	public String getAdv_code() {
		return adv_code;
	}

	public void setAdv_code(String adv_code) {
		this.adv_code = adv_code;
	}

	public String getAd_name() {
		return ad_name;
	}

	public void setAd_name(String ad_name) {
		this.ad_name = ad_name;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {

		this.img_path = img_path;
	}

	public String getLink_url() {
		return link_url;
	}

	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		if (start_time != null && start_time.indexOf(".0") > -1)
			start_time = start_time.replace(".0", "");
		this.start_time = start_time;
	}

	public String getEnd_time() {
		if (end_time != null && end_time.indexOf(".0") > -1)
			end_time = end_time.replace(".0", "");
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
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
