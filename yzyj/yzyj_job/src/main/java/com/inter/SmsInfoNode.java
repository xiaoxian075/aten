package com.inter;

import java.io.Serializable;
import java.math.BigDecimal;

public class SmsInfoNode implements Serializable {
	private static final long serialVersionUID = -4636636008103165268L;
	
	private int code;
	private String msg;
	private int count;
	private BigDecimal fee;
	private String unit;
	private String mobile;
	private String sid;
	public SmsInfoNode() {
		super();
	}
	public SmsInfoNode(int code, String msg, int count, BigDecimal fee, String unit, String mobile, String sid) {
		super();
		this.code = code;
		this.msg = msg;
		this.count = count;
		this.fee = fee;
		this.unit = unit;
		this.mobile = mobile;
		this.sid = sid;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	@Override
	public String toString() {
		return "SmsInfoNode [code=" + code + ", msg=" + msg + ", count=" + count + ", fee=" + fee + ", unit=" + unit
				+ ", mobile=" + mobile + ", sid=" + sid + "]";
	}
}
