package com.aten.model.orm;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

public class OrderRefundNode implements Serializable {
	private static final long serialVersionUID = 7788703149993689827L;
	
	private String refund_id;		//退款编号
	private BigInteger detail_id;	//子订单编号
	private String goods_name;		//商品名称
	private BigInteger seller_id;	//卖家标识
	private String seller_nick;		//卖家昵称
	private BigInteger account_id;	//退款人标识
	private String account_name;	//退款人姓名
	private String buyer_nick;		//退款申请人
	private String login_name;		//云支付帐号
	private int refund_type;		//退款类型  0：退款 1：退货 2：售后订单
	private String refund_reason;	//退款原因  字典
	private BigInteger refund_amount;	//退款金额
	private int refund_state;		//退款状态  1:待商家同意(申请退货退款) 2:取消申请 (退货退款)  3:同意申请(待买家发货) 4:已退货(待卖家同意) 5:拒绝申请 6:退货时间超时 7:同意退款(流程结束) 
	private Timestamp refund_time;	//退款申请时间
	private Timestamp time_out;		//超时时间

	public OrderRefundNode() {
		super();
	}
	public OrderRefundNode(String refund_id, BigInteger detail_id, String goods_name, BigInteger seller_id,
			String seller_nick, BigInteger account_id, String account_name, String buyer_nick, String login_name, int refund_type,
			String refund_reason, BigInteger refund_amount, int refund_state,
			Timestamp refund_time, Timestamp time_out) {
		super();
		this.refund_id = refund_id;
		this.detail_id = detail_id;
		this.goods_name = goods_name;
		this.seller_id = seller_id;
		this.seller_nick = seller_nick;
		this.account_id = account_id;
		this.account_name = account_name;
		this.buyer_nick = buyer_nick;
		this.login_name = login_name;
		this.refund_type = refund_type;
		this.refund_reason = refund_reason;
		this.refund_amount = refund_amount;
		this.refund_state = refund_state;
		this.refund_time = refund_time;
		this.time_out = time_out;
	}
	public String getRefund_id() {
		return refund_id;
	}
	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}
	public BigInteger getDetail_id() {
		return detail_id;
	}
	public void setDetail_id(BigInteger detail_id) {
		this.detail_id = detail_id;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public BigInteger getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(BigInteger seller_id) {
		this.seller_id = seller_id;
	}
	public String getSeller_nick() {
		return seller_nick;
	}
	public void setSeller_nick(String seller_nick) {
		this.seller_nick = seller_nick;
	}
	public BigInteger getAccount_id() {
		return account_id;
	}
	public void setAccount_id(BigInteger account_id) {
		this.account_id = account_id;
	}
	public String getBuyer_nick() {
		return buyer_nick;
	}
	public void setBuyer_nick(String buyer_nick) {
		this.buyer_nick = buyer_nick;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public int getRefund_type() {
		return refund_type;
	}
	public void setRefund_type(int refund_type) {
		this.refund_type = refund_type;
	}
	public String getRefund_reason() {
		return refund_reason;
	}
	public void setRefund_reason(String refund_reason) {
		this.refund_reason = refund_reason;
	}
	public BigInteger getRefund_amount() {
		return refund_amount;
	}
	public void setRefund_amount(BigInteger refund_amount) {
		this.refund_amount = refund_amount;
	}
	public int getRefund_state() {
		return refund_state;
	}
	public void setRefund_state(int refund_state) {
		this.refund_state = refund_state;
	}
	public Timestamp getRefund_time() {
		return refund_time;
	}
	public void setRefund_time(Timestamp refund_time) {
		this.refund_time = refund_time;
	}
	public Timestamp getTime_out() {
		return time_out;
	}
	public void setTime_out(Timestamp time_out) {
		this.time_out = time_out;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
}
