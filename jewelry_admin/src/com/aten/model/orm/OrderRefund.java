package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

import com.communal.util.StringUtil;

public class OrderRefund implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String refund_id;
	private String detail_id;
	private String goods_name;
	private String seller_id;
	private String seller_nick;
	private String account_id;
	private String buyer_nick;
	private String refund_type;
	private String refund_reason;
	private String refund_amount;
	private String refund_explain;
	private String refund_img;
	private String refund_state;
	private String refund_time;
	private String time_out;

	
	
	
	public String getRefund_id() {
		return refund_id;
	}
	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}
	
	public String getDetail_id() {
		return detail_id;
	}
	public void setDetail_id(String detail_id) {
		this.detail_id = detail_id;
	}
	
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	
	public String getSeller_nick() {
		return seller_nick;
	}
	public void setSeller_nick(String seller_nick) {
		this.seller_nick = seller_nick;
	}
	
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	
	public String getBuyer_nick() {
		return buyer_nick;
	}
	public void setBuyer_nick(String buyer_nick) {
		this.buyer_nick = buyer_nick;
	}
	
	public String getRefund_type() {
		return refund_type;
	}
	public void setRefund_type(String refund_type) {
		this.refund_type = refund_type;
	}
	
	public String getRefund_reason() {
		return refund_reason;
	}
	public void setRefund_reason(String refund_reason) {
		this.refund_reason = refund_reason;
	}
	
	public String getRefund_amount() {
		return refund_amount;
	}
	public void setRefund_amount(String refund_amount) {
		this.refund_amount = refund_amount;
	}
	
	public String getRefund_explain() {
		return refund_explain;
	}
	public void setRefund_explain(String refund_explain) {
		this.refund_explain = refund_explain;
	}
	
	public String getRefund_img() {
		return refund_img;
	}
	public void setRefund_img(String refund_img) {
		this.refund_img = refund_img;
	}
	
	public String getRefund_state() {
		return refund_state;
	}
	public void setRefund_state(String refund_state) {
		this.refund_state = refund_state;
	}
	
	public String getRefund_time() {
		return refund_time;
	}
	public void setRefund_time(String refund_time) {
		this.refund_time = StringUtil.getStandDate(refund_time);
	}
	
	public String getTime_out() {
		return time_out;
	}
	public void setTime_out(String time_out) {
		this.time_out = StringUtil.getStandDate(time_out);
	}
	
	
}

