package com.aten.model.orm;


import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

public class AccountBill implements Serializable {
	private static final long serialVersionUID = -4855431586637256105L;

	private BigInteger id;
	private Timestamp create_time;	//创建时间
	private String bill_number;		//交易流水号
	private BigInteger account_id;	//帐户标识
	private String bill_name;		//账单名称
	private String order_id;		//订单号
	private int pay_way;			//支付方式 1 余额支付 2 云付通支付
	private int bill_type;			//帐单类型  1 系统余额充值 2 系统余额扣款 3 消费 4 退款 5 会员升级
	private int io_type;			//收支类型  1  支出  2 收入 
	private BigInteger amount;		//金额
	private String note;			//备注
	
	private String pay_way_str;
	private String io_type_str;
	private String amount_str;
	private String create_time_str;

	public String getPay_way_str() {
		return pay_way_str;
	}
	public void setPay_way_str(String pay_way_str) {
		this.pay_way_str = pay_way_str;
	}
	public String getIo_type_str() {
		return io_type_str;
	}
	public void setIo_type_str(String io_type_str) {
		this.io_type_str = io_type_str;
	}
	public String getAmount_str() {
		return amount_str;
	}
	public void setAmount_str(String amount_str) {
		this.amount_str = amount_str;
	}
	public String getCreate_time_str() {
		return create_time_str;
	}
	public void setCreate_time_str(String create_time_str) {
		this.create_time_str = create_time_str;
	}
	public AccountBill() {
		super();
	}
	public AccountBill(BigInteger id, Timestamp create_time, String bill_number, BigInteger account_id, String bill_name, String order_id,
			int pay_way, int bill_type, int io_type, BigInteger amount, String note) {
		super();
		this.id = id;
		this.create_time = create_time;
		this.bill_number = bill_number;
		this.account_id = account_id;
		this.bill_name = bill_name;
		this.order_id = order_id;
		this.pay_way = pay_way;
		this.bill_type = bill_type;
		this.io_type = io_type;
		this.amount = amount;
		this.note = note;
	}
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public String getBill_number() {
		return bill_number;
	}
	public void setBill_number(String bill_number) {
		this.bill_number = bill_number;
	}
	public BigInteger getAccount_id() {
		return account_id;
	}
	public void setAccount_id(BigInteger account_id) {
		this.account_id = account_id;
	}
	public String getBill_name() {
		return bill_name;
	}
	public void setBill_name(String bill_name) {
		this.bill_name = bill_name;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public int getPay_way() {
		return pay_way;
	}
	public void setPay_way(int pay_way) {
		this.pay_way = pay_way;
	}
	public int getBill_type() {
		return bill_type;
	}
	public void setBill_type(int bill_type) {
		this.bill_type = bill_type;
	}
	public int getIo_type() {
		return io_type;
	}
	public void setIo_type(int io_type) {
		this.io_type = io_type;
	}
	public BigInteger getAmount() {
		return amount;
	}
	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}

//private String id;
//private String create_time;
//private String bill_number;
//private String account_id;
//private String bill_name;
//private String order_id;
//private String pay_way;
//private String bill_type;
//private String io_type;
//private String amount;
//private String note;
//
//
//
//
//public String getId() {
//	return id;
//}
//public void setId(String id) {
//	this.id = id;
//}
//
//public String getCreate_time() {
//	return create_time;
//}
//public void setCreate_time(String create_time) {
//	this.create_time = create_time;
//}
//
//public String getBill_number() {
//	return bill_number;
//}
//public void setBill_number(String bill_number) {
//	this.bill_number = bill_number;
//}
//
//public String getAccount_id() {
//	return account_id;
//}
//public void setAccount_id(String account_id) {
//	this.account_id = account_id;
//}
//
//public String getBill_name() {
//	return bill_name;
//}
//public void setBill_name(String bill_name) {
//	this.bill_name = bill_name;
//}
//
//public String getOrder_id() {
//	return order_id;
//}
//public void setOrder_id(String order_id) {
//	this.order_id = order_id;
//}
//
//public String getPay_way() {
//	return pay_way;
//}
//public void setPay_way(String pay_way) {
//	this.pay_way = pay_way;
//}
//
//public String getBill_type() {
//	return bill_type;
//}
//public void setBill_type(String bill_type) {
//	this.bill_type = bill_type;
//}
//
//public String getIo_type() {
//	return io_type;
//}
//public void setIo_type(String io_type) {
//	this.io_type = io_type;
//}
//
//public String getAmount() {
//	return amount;
//}
//public void setAmount(String amount) {
//	this.amount = amount;
//}
//
//public String getNote() {
//	return note;
//}
//public void setNote(String note) {
//	this.note = note;
//}

