package com.aten.model.orm;

import java.io.Serializable;
import java.math.BigInteger;

public class CustomAttrValueNode implements Serializable{
	private static final long serialVersionUID = 6756457130319282594L;
	private BigInteger sav_id;
	private BigInteger custom_attr_id;
	private BigInteger attr_id;
	private BigInteger av_id;
	private String custom_av_id;
	private String custom_attr_value;
	private String custom_attr_img;
	private String relate_img;
	public BigInteger getSav_id() {
		return sav_id;
	}
	public void setSav_id(BigInteger sav_id) {
		this.sav_id = sav_id;
	}
	public BigInteger getCustom_attr_id() {
		return custom_attr_id;
	}
	public void setCustom_attr_id(BigInteger custom_attr_id) {
		this.custom_attr_id = custom_attr_id;
	}
	public BigInteger getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(BigInteger attr_id) {
		this.attr_id = attr_id;
	}
	public BigInteger getAv_id() {
		return av_id;
	}
	public void setAv_id(BigInteger av_id) {
		this.av_id = av_id;
	}
	public String getCustom_av_id() {
		return custom_av_id;
	}
	public void setCustom_av_id(String custom_av_id) {
		this.custom_av_id = custom_av_id;
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
