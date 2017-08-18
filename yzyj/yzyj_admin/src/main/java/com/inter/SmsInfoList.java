package com.inter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class SmsInfoList implements Serializable{
	private static final long serialVersionUID = -44478220125685807L;
	
	private int total_count;
	private BigDecimal total_fee;
	private String unit;
	private List<SmsInfoNode> data;
	public SmsInfoList() {
		super();
	}
	public SmsInfoList(int total_count, BigDecimal total_fee, String unit, List<SmsInfoNode> data) {
		super();
		this.total_count = total_count;
		this.total_fee = total_fee;
		this.unit = unit;
		this.data = data;
	}
	public int getTotal_count() {
		return total_count;
	}
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}
	public BigDecimal getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(BigDecimal total_fee) {
		this.total_fee = total_fee;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public List<SmsInfoNode> getData() {
		return data;
	}
	public void setData(List<SmsInfoNode> data) {
		this.data = data;
	}
}
