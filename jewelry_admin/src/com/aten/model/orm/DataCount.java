package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class DataCount implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String dc_id;
	private String statistics_date;
	private String settle_date;
	private String order_deal_count;
	private String deal_total_amount;
	private String deal_man_num;
	private String plat_activity_num;
	private String avg_price;
	private String plat_all_member_count;
	private String plat_inc_member_count;
	private String plat_vip_member_count;
	private String plat_vip_inc_member_count;

	
	
	
	public String getDc_id() {
		return dc_id;
	}
	public void setDc_id(String dc_id) {
		this.dc_id = dc_id;
	}
	
	public String getStatistics_date() {
		return statistics_date;
	}
	public void setStatistics_date(String statistics_date) {
		this.statistics_date = statistics_date;
	}
	
	public String getSettle_date() {
		return settle_date;
	}
	public void setSettle_date(String settle_date) {
		this.settle_date = settle_date;
	}
	
	public String getOrder_deal_count() {
		return order_deal_count;
	}
	public void setOrder_deal_count(String order_deal_count) {
		this.order_deal_count = order_deal_count;
	}
	
	public String getDeal_total_amount() {
		return deal_total_amount;
	}
	public void setDeal_total_amount(String deal_total_amount) {
		this.deal_total_amount = deal_total_amount;
	}
	
	public String getDeal_man_num() {
		return deal_man_num;
	}
	public void setDeal_man_num(String deal_man_num) {
		this.deal_man_num = deal_man_num;
	}
	
	public String getPlat_activity_num() {
		return plat_activity_num;
	}
	public void setPlat_activity_num(String plat_activity_num) {
		this.plat_activity_num = plat_activity_num;
	}
	
	public String getAvg_price() {
		return avg_price;
	}
	public void setAvg_price(String avg_price) {
		this.avg_price = avg_price;
	}
	
	public String getPlat_all_member_count() {
		return plat_all_member_count;
	}
	public void setPlat_all_member_count(String plat_all_member_count) {
		this.plat_all_member_count = plat_all_member_count;
	}
	
	public String getPlat_inc_member_count() {
		return plat_inc_member_count;
	}
	public void setPlat_inc_member_count(String plat_inc_member_count) {
		this.plat_inc_member_count = plat_inc_member_count;
	}
	
	public String getPlat_vip_member_count() {
		return plat_vip_member_count;
	}
	public void setPlat_vip_member_count(String plat_vip_member_count) {
		this.plat_vip_member_count = plat_vip_member_count;
	}
	
	public String getPlat_vip_inc_member_count() {
		return plat_vip_inc_member_count;
	}
	public void setPlat_vip_inc_member_count(String plat_vip_inc_member_count) {
		this.plat_vip_inc_member_count = plat_vip_inc_member_count;
	}
	
	
}

