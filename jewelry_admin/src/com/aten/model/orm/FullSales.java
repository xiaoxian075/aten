package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class FullSales implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String full_id;
	private String goods_id;
	private String full_presale_endtime;
	private String full_send_time_type;
	private String full_send_day_num;
	private String full_send_time;
	private String full_limit_buy_num;

	
	
	
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
	
	public String getFull_presale_endtime() {
		return full_presale_endtime;
	}
	public void setFull_presale_endtime(String full_presale_endtime) {
		this.full_presale_endtime = full_presale_endtime;
	}
	
	public String getFull_send_time_type() {
		return full_send_time_type;
	}
	public void setFull_send_time_type(String full_send_time_type) {
		this.full_send_time_type = full_send_time_type;
	}
	
	public String getFull_send_day_num() {
		return full_send_day_num;
	}
	public void setFull_send_day_num(String full_send_day_num) {
		this.full_send_day_num = full_send_day_num;
	}
	
	public String getFull_send_time() {
		return full_send_time;
	}
	public void setFull_send_time(String full_send_time) {
		this.full_send_time = full_send_time;
	}
	
	public String getFull_limit_buy_num() {
		return full_limit_buy_num;
	}
	public void setFull_limit_buy_num(String full_limit_buy_num) {
		this.full_limit_buy_num = full_limit_buy_num;
	}
	
	
}

