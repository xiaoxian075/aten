package com.aten.model.orm;


import com.communal.util.StringUtil;

import java.io.Serializable;
import java.util.Date;

public class ManaBills implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String mb_id;
	private String seller_id;
	private String trade_num;
	private String bill_amount;
	private String trade_amount;
	private String statistics_date;
	private String settle_date;
	private String divided_amount;

	
	
	
	public String getMb_id() {
		return mb_id;
	}
	public void setMb_id(String mb_id) {
		this.mb_id = mb_id;
	}
	
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	
	public String getTrade_num() {
		return trade_num;
	}
	public void setTrade_num(String trade_num) {
		this.trade_num = trade_num;
	}
	
	public String getBill_amount() {
		return bill_amount;
	}
	public void setBill_amount(String bill_amount) {
		this.bill_amount = bill_amount;
	}
	
	public String getTrade_amount() {
		return trade_amount;
	}
	public void setTrade_amount(String trade_amount) {
		this.trade_amount = trade_amount;
	}
	
	public String getStatistics_date() {
		return StringUtil.getStandDate(statistics_date);
	}
	public void setStatistics_date(String statistics_date) {
		this.statistics_date = statistics_date;
	}
	
	public String getSettle_date() {
		return StringUtil.getStandDate(settle_date);
	}
	public void setSettle_date(String settle_date) {
		this.settle_date = settle_date;
	}
	
	public String getDivided_amount() {
		return divided_amount;
	}
	public void setDivided_amount(String divided_amount) {
		this.divided_amount = divided_amount;
	}
	
	
}

