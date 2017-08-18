package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class GoodsCustomAttrValue implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String sav_id;
	private String custom_attr_id;
	private String attr_id;
	private String av_id;
	private String custom_attr_value;
	private String custom_attr_img;
	private String relate_img;
	private String custom_av_id;
	

	
	public String getCustom_av_id() {
		return custom_av_id;
	}
	public void setCustom_av_id(String custom_av_id) {
		this.custom_av_id = custom_av_id;
	}
	public String getSav_id() {
		return sav_id;
	}
	public void setSav_id(String sav_id) {
		this.sav_id = sav_id;
	}
	
	public String getCustom_attr_id() {
		return custom_attr_id;
	}
	public void setCustom_attr_id(String custom_attr_id) {
		this.custom_attr_id = custom_attr_id;
	}
	
	public String getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
	}
	
	public String getAv_id() {
		return av_id;
	}
	public void setAv_id(String av_id) {
		this.av_id = av_id;
	}
	
	public String getCustom_attr_value() {
		return custom_attr_value;
	}
	public void setCustom_attr_value(String custom_attr_value) {
		this.custom_attr_value = custom_attr_value;
	}
	
	public String getCustom_attr_img() {
		return custom_attr_img;
	}
	public void setCustom_attr_img(String custom_attr_img) {
		this.custom_attr_img = custom_attr_img;
	}
	
	public String getRelate_img() {
		return relate_img;
	}
	public void setRelate_img(String relate_img) {
		this.relate_img = relate_img;
	}
	
	
}

