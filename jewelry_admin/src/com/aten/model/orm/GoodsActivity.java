package com.aten.model.orm;


import com.communal.util.StringUtil;

import java.io.Serializable;
import java.util.Date;

public class GoodsActivity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String activity_id;
	private String activity_type;
	private String activity_state;
	private String state;
	private String activity_name;
	private String introduce;
	private String activity_img;
	private String discount;
	private String start_time;
	private String end_time;
	private String[] goodsIds;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String[] getGoodsIds() {
		return goodsIds;
	}

	public void setGoodsIds(String[] goodsIds) {
		this.goodsIds = goodsIds;
	}

	public String getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}

	public String getActivity_type() {
		return activity_type;
	}
	public void setActivity_type(String activity_type) {
		this.activity_type = activity_type;
	}
	
	public String getActivity_state() {
		return activity_state;
	}
	public void setActivity_state(String activity_state) {
		this.activity_state = activity_state;
	}
	
	public String getActivity_name() {
		return activity_name;
	}
	public void setActivity_name(String activity_name) {
		this.activity_name = activity_name;
	}
	
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
	public String getActivity_img() {
		return activity_img;
	}
	public void setActivity_img(String activity_img) {
		this.activity_img = activity_img;
	}
	
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	public String getStart_time() {
		return StringUtil.getStandDate(start_time);
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	
	public String getEnd_time() {
		return StringUtil.getStandDate(end_time);
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	
	
}

