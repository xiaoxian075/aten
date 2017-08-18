package com.aten.model.orm;

import com.communal.util.StringUtil;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

public class BillFlow implements Serializable {
	private static final long serialVersionUID = -5737059504882934462L;
	
	private BigInteger bf_id;
	private BigInteger seller_id;	//运营商标识
	private String trade_id;		//交易流水号
	private BigInteger bill_amount;	//金额\
	private String amount;
	private int bill_type;			//收益类型  0:支出 1：收入
	private Timestamp bill_time;	//帐单时间
	private String bill_time_str;
	private String order_type;		//订单类型   0：消费  1：退款 2：系统扣减 3：系统充值 4:提现 5：VIP升级
	private String order_type_name;	//订单类型名称
	private String pay_way;			//支付方式	 1 余额支付 2 云付通支付
	private String pay_way_name;	//支付方式名称
	private String ralation_id;		//关联单号
	private String note;			//备注
	private String oper_man;		//操作人
	private Timestamp oper_time;	//操作时间
	public BillFlow() {
		super();
	}
	public BillFlow(BigInteger bf_id, BigInteger seller_id, String trade_id, BigInteger bill_amount, int bill_type,
			Timestamp bill_time, String order_type, String order_type_name, String pay_way, String pay_way_name,
			String ralation_id, String note, String oper_man, Timestamp oper_time) {
		super();
		this.bf_id = bf_id;
		this.seller_id = seller_id;
		this.trade_id = trade_id;
		this.bill_amount = bill_amount;
		this.bill_type = bill_type;
		this.bill_time = bill_time;
		this.order_type = order_type;
		this.order_type_name = order_type_name;
		this.pay_way = pay_way;
		this.pay_way_name = pay_way_name;
		this.ralation_id = ralation_id;
		this.note = note;
		this.oper_man = oper_man;
		this.oper_time = oper_time;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public BigInteger getBf_id() {
		return bf_id;
	}
	public void setBf_id(BigInteger bf_id) {
		this.bf_id = bf_id;
	}
	public BigInteger getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(BigInteger seller_id) {
		this.seller_id = seller_id;
	}
	public String getTrade_id() {
		return trade_id;
	}
	public void setTrade_id(String trade_id) {
		this.trade_id = trade_id;
	}
	public BigInteger getBill_amount() {
		return bill_amount;
	}
	public void setBill_amount(BigInteger bill_amount) {
		this.bill_amount = bill_amount;
	}
	public int getBill_type() {
		return bill_type;
	}
	public void setBill_type(int bill_type) {
		this.bill_type = bill_type;
	}
	public Timestamp getBill_time() {
		return bill_time;
	}

	public String getBill_time_str() {
		return StringUtil.getStandDate(bill_time.toString());
	}

	public void setBill_time_str(String bill_time_str) {
		this.bill_time_str = bill_time_str;
	}

	public void setBill_time(Timestamp bill_time) {
		this.bill_time = bill_time;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getOrder_type_name() {
		return order_type_name;
	}
	public void setOrder_type_name(String order_type_name) {
		this.order_type_name = order_type_name;
	}
	public String getPay_way() {
		return pay_way;
	}
	public void setPay_way(String pay_way) {
		this.pay_way = pay_way;
	}
	public String getPay_way_name() {
		return pay_way_name;
	}
	public void setPay_way_name(String pay_way_name) {
		this.pay_way_name = pay_way_name;
	}
	public String getRalation_id() {
		return ralation_id;
	}
	public void setRalation_id(String ralation_id) {
		this.ralation_id = ralation_id;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getOper_man() {
		return oper_man;
	}
	public void setOper_man(String oper_man) {
		this.oper_man = oper_man;
	}
	public Timestamp getOper_time() {
		return oper_time;
	}
	public void setOper_time(Timestamp oper_time) {
		this.oper_time = oper_time;
	}

}
