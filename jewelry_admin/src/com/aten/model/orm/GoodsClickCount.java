package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class GoodsClickCount implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String gcc_id;
	private String statistics_date;
	private String total_goods_id;
	private String total_goods_name;
	private String total_click_count;
	private String today_goods_id;
	private String today_goods_name;
	private String today_click_count;
	private String settle_date;

	
	
	
	public String getGcc_id() {
		return gcc_id;
	}
	public void setGcc_id(String gcc_id) {
		this.gcc_id = gcc_id;
	}
	
	public String getStatistics_date() {
		return statistics_date;
	}
	public void setStatistics_date(String statistics_date) {
		this.statistics_date = statistics_date;
	}
	
	public String getTotal_goods_id() {
		return total_goods_id;
	}
	public void setTotal_goods_id(String total_goods_id) {
		this.total_goods_id = total_goods_id;
	}
	
	public String getTotal_goods_name() {
		return total_goods_name;
	}
	public void setTotal_goods_name(String total_goods_name) {
		this.total_goods_name = total_goods_name;
	}
	
	public String getTotal_click_count() {
		return total_click_count;
	}
	public void setTotal_click_count(String total_click_count) {
		this.total_click_count = total_click_count;
	}
	
	public String getToday_goods_id() {
		return today_goods_id;
	}
	public void setToday_goods_id(String today_goods_id) {
		this.today_goods_id = today_goods_id;
	}
	
	public String getToday_goods_name() {
		return today_goods_name;
	}
	public void setToday_goods_name(String today_goods_name) {
		this.today_goods_name = today_goods_name;
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

