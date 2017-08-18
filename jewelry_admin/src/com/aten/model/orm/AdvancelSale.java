package com.aten.model.orm;


import java.io.Serializable;

public class AdvancelSale implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String advancel_id;
	private String goods_id;
	private String presale_endtime;
	private String pre_send_time_type;
	private String pre_send_time;
	private String pre_hold_time;
	private String pre_limit_buy_num;

	
	public String getAdvancel_id() {
		return advancel_id;
	}
	public void setAdvancel_id(String advancel_id) {
		this.advancel_id = advancel_id;
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
	
	public String getPre_send_time_type() {
		return pre_send_time_type;
	}
	public void setPre_send_time_type(String pre_send_time_type) {
		this.pre_send_time_type = pre_send_time_type;
	}
	
	public String getPre_send_time() {
		return pre_send_time;
	}
	public void setPre_send_time(String pre_send_time) {
		this.pre_send_time = pre_send_time;
	}
	
	public String getPre_hold_time() {
		return pre_hold_time;
	}
	public void setPre_hold_time(String pre_hold_time) {
		this.pre_hold_time = pre_hold_time;
	}
	
	public String getPre_limit_buy_num() {
		return pre_limit_buy_num;
	}
	public void setPre_limit_buy_num(String pre_limit_buy_num) {
		this.pre_limit_buy_num = pre_limit_buy_num;
	}
	
	
}

