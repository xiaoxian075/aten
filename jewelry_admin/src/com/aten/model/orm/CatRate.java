package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class CatRate implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String rate_id;
	private String cat_id;
	private String divide_rate;
	private String  manual_fee;

	public String getManual_fee() {
		return manual_fee;
	}

	public void setManual_fee(String manual_fee) {
		this.manual_fee = manual_fee;
	}

	public String getRate_id() {
		return rate_id;
	}
	public void setRate_id(String rate_id) {
		this.rate_id = rate_id;
	}
	
	public String getCat_id() {
		return cat_id;
	}
	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}
	
	public String getDivide_rate() {
		return divide_rate;
	}
	public void setDivide_rate(String divide_rate) {
		this.divide_rate = divide_rate;
	}
	
	
}

