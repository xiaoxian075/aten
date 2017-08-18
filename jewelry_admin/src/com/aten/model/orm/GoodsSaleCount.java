package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class GoodsSaleCount implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String gsc_id;
	private String statistics_date;
	private String total_goods_id;
	private String total_goods_name;
	private String total_sale_count;
	private String today_goods_id;
	private String today_goods_name;
	private String today_sale_count;
	private String settle_date;

	
	
	
	public String getGsc_id() {
		return gsc_id;
	}
	public void setGsc_id(String gsc_id) {
		this.gsc_id = gsc_id;
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
	
	public String getTotal_sale_count() {
		return total_sale_count;
	}
	public void setTotal_sale_count(String total_sale_count) {
		this.total_sale_count = total_sale_count;
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
	
	public String getToday_sale_count() {
		return today_sale_count;
	}
	public void setToday_sale_count(String today_sale_count) {
		this.today_sale_count = today_sale_count;
	}
	
	public String getSettle_date() {
		return settle_date;
	}
	public void setSettle_date(String settle_date) {
		this.settle_date = settle_date;
	}
	
	
}

