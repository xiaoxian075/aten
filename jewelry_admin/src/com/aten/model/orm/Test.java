package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class Test implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String full_id;
	private String goods_id;
	private String presale_endtime;
	private String send_time_type;
	private String send_day_num;
	private String send_time;
	private String limit_buy_num;

	
	
	
	public String getFull_id() {
		return full_id;
	}
	public void setFull_id(String full_id) {
		this.full_id = full_id;
	}
	
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	
	public String getPresale_endtime() {
		return presale_endtime;
	}
	public void setPresale_endtime(String presale_endtime) {
		this.presale_endtime = presale_endtime;
	}
	
	public String getSend_time_type() {
		return send_time_type;
	}
	public void setSend_time_type(String send_time_type) {
		this.send_time_type = send_time_type;
	}
	
	public String getSend_day_num() {
		return send_day_num;
	}
	public void setSend_day_num(String send_day_num) {
		this.send_day_num = send_day_num;
	}
	
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	
	public String getLimit_buy_num() {
		return limit_buy_num;
	}
	public void setLimit_buy_num(String limit_buy_num) {
		this.limit_buy_num = limit_buy_num;
	}
	
	
}

