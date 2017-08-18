package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class Fastmail implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String fast_id;
	private String fast_code;
	private String fast_name;
	private String fast_logo;
	private String sort_no;
	private String is_insured;
	private String fast_desc;
	private String rate;
	private String mix_insured;
	private String max_insured;
	private String is_reach_pay;
	private String default_temp;
	private String waybill_rule;
	private String state;

	
	
	
	public String getFast_id() {
		return fast_id;
	}
	public void setFast_id(String fast_id) {
		this.fast_id = fast_id;
	}
	
	public String getFast_code() {
		return fast_code;
	}
	public void setFast_code(String fast_code) {
		this.fast_code = fast_code;
	}
	
	public String getFast_name() {
		return fast_name;
	}
	public void setFast_name(String fast_name) {
		this.fast_name = fast_name;
	}
	
	public String getFast_logo() {
		return fast_logo;
	}
	public void setFast_logo(String fast_logo) {
		this.fast_logo = fast_logo;
	}
	
	public String getSort_no() {
		return sort_no;
	}
	public void setSort_no(String sort_no) {
		this.sort_no = sort_no;
	}
	
	public String getIs_insured() {
		return is_insured;
	}
	public void setIs_insured(String is_insured) {
		this.is_insured = is_insured;
	}
	
	public String getFast_desc() {
		return fast_desc;
	}
	public void setFast_desc(String fast_desc) {
		this.fast_desc = fast_desc;
	}
	
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	
	public String getMix_insured() {
		return mix_insured;
	}
	public void setMix_insured(String mix_insured) {
		this.mix_insured = mix_insured;
	}
	
	public String getMax_insured() {
		return max_insured;
	}
	public void setMax_insured(String max_insured) {
		this.max_insured = max_insured;
	}
	
	public String getIs_reach_pay() {
		return is_reach_pay;
	}
	public void setIs_reach_pay(String is_reach_pay) {
		this.is_reach_pay = is_reach_pay;
	}
	
	public String getDefault_temp() {
		return default_temp;
	}
	public void setDefault_temp(String default_temp) {
		this.default_temp = default_temp;
	}
	
	public String getWaybill_rule() {
		return waybill_rule;
	}
	public void setWaybill_rule(String waybill_rule) {
		this.waybill_rule = waybill_rule;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}

