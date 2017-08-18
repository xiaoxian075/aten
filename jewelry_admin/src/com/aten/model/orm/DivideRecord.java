package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class DivideRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String dr_record;
	private String rate_id;
	private String divide_rate;
	private String oper_man_id;
	private String oper_man;
	private String oper_time;
	
	
	public String getDr_record() {
		return dr_record;
	}
	public void setDr_record(String dr_record) {
		this.dr_record = dr_record;
	}
	
	public String getRate_id() {
		return rate_id;
	}
	public void setRate_id(String rate_id) {
		this.rate_id = rate_id;
	}
	
	public String getDivide_rate() {
		return divide_rate;
	}
	public void setDivide_rate(String divide_rate) {
		this.divide_rate = divide_rate;
	}
	
	public String getOper_man_id() {
		return oper_man_id;
	}
	public void setOper_man_id(String oper_man_id) {
		this.oper_man_id = oper_man_id;
	}
	
	public String getOper_man() {
		return oper_man;
	}
	public void setOper_man(String oper_man) {
		this.oper_man = oper_man;
	}
	
	public String getOper_time() {
		return oper_time;
	}
	public void setOper_time(String oper_time) {
		this.oper_time = oper_time;
	}
	
	
}

