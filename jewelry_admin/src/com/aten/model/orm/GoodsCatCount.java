package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class GoodsCatCount implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String gac_id;
	private String statistics_date;
	private String total_cat_id;
	private String total_cat_name;
	private String total_click_count;
	private String today_cat_id;
	private String today_cat_name;
	private String today_click_count;
	private String settle_date;

	
	
	
	public String getGac_id() {
		return gac_id;
	}
	public void setGac_id(String gac_id) {
		this.gac_id = gac_id;
	}
	
	public String getStatistics_date() {
		return statistics_date;
	}
	public void setStatistics_date(String statistics_date) {
		this.statistics_date = statistics_date;
	}
	
	public String getTotal_cat_id() {
		return total_cat_id;
	}
	public void setTotal_cat_id(String total_cat_id) {
		this.total_cat_id = total_cat_id;
	}
	
	public String getTotal_cat_name() {
		return total_cat_name;
	}
	public void setTotal_cat_name(String total_cat_name) {
		this.total_cat_name = total_cat_name;
	}
	
	public String getTotal_click_count() {
		return total_click_count;
	}
	public void setTotal_click_count(String total_click_count) {
		this.total_click_count = total_click_count;
	}
	
	public String getToday_cat_id() {
		return today_cat_id;
	}
	public void setToday_cat_id(String today_cat_id) {
		this.today_cat_id = today_cat_id;
	}
	
	public String getToday_cat_name() {
		return today_cat_name;
	}
	public void setToday_cat_name(String today_cat_name) {
		this.today_cat_name = today_cat_name;
	}
	
	public String getToday_click_count() {
		return today_click_count;
	}
	public void setToday_click_count(String today_click_count) {
		this.today_click_count = today_click_count;
	}
	
	public String getSettle_date() {
		return settle_date;
	}
	public void setSettle_date(String settle_date) {
		this.settle_date = settle_date;
	}
	
	
}

