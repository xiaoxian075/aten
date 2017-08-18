package com.aten.model.orm;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

public class OrderRefundLogsNode implements Serializable {
	private static final long serialVersionUID = -530297293709496369L;
	
	private BigInteger refund_log_id;	//退货退款记录标识
	private String refund_id;			//退款编号  退款编号 与订单的生成方式一样
	private String seller_nick;			//卖家名称
	private String buyer_nick;			//买家名称
	private String refund_img;			//上传图片
	private String record_explain;		//说明
	private String record_title;		//记录标题
	private Timestamp record_time;		//记录时间
	private int type;					//1:卖家 2：买家
	public OrderRefundLogsNode() {
		super();
	}
	public OrderRefundLogsNode(BigInteger refund_log_id, String refund_id, String seller_nick, String buyer_nick,
			String refund_img, String record_explain, String record_title, Timestamp record_time,int type) {
		super();
		this.refund_log_id = refund_log_id;
		this.refund_id = refund_id;
		this.seller_nick = seller_nick;
		this.buyer_nick = buyer_nick;
		this.refund_img = refund_img;
		this.record_explain = record_explain;
		this.record_title = record_title;
		this.record_time = record_time;
		this.type = type;
	}
	public BigInteger getRefund_log_id() {
		return refund_log_id;
	}
	public void setRefund_log_id(BigInteger refund_log_id) {
		this.refund_log_id = refund_log_id;
	}
	public String getRefund_id() {
		return refund_id;
	}
	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}
	public String getSeller_nick() {
		return seller_nick;
	}
	public void setSeller_nick(String seller_nick) {
		this.seller_nick = seller_nick;
	}
	public String getBuyer_nick() {
		return buyer_nick;
	}
	public void setBuyer_nick(String buyer_nick) {
		this.buyer_nick = buyer_nick;
	}
	public String getRefund_img() {
		return refund_img;
	}
	public void setRefund_img(String refund_img) {
		this.refund_img = refund_img;
	}
	public String getRecord_explain() {
		return record_explain;
	}
	public void setRecord_explain(String record_explain) {
		this.record_explain = record_explain;
	}
	public String getRecord_title() {
		return record_title;
	}
	public void setRecord_title(String record_title) {
		this.record_title = record_title;
	}
	public Timestamp getRecord_time() {
		return record_time;
	}
	public void setRecord_time(Timestamp record_time) {
		this.record_time = record_time;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
