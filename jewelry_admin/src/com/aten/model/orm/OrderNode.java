package com.aten.model.orm;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

public class OrderNode implements Serializable{
	private static final long serialVersionUID = 7939470186745420372L;
	
	private BigInteger order_id;
	private String order_number;	//订单编号
	private String pay_number;		//支付订单号
	private int order_state;		//订单状态
									/*	            0：未付款
										            1：等待卖家发货
										            2：等待买家确认收货
										            3：交易成功
										            5： 交易关闭
										            6：末付款前交易关闭
									*/	   
	private int pay_state;			//支付状态	0:未付款  1:已付款
	private int order_type;			//订单类型  1 正常订单  2 预付订单
	private BigInteger seller_id;	//卖家标识
	private BigInteger account_id;	//帐户ID
	private String account_name;
	private String consignee;		//收件人
	private String consignee_area;	//收件人地区
	private String consignee_address;	//收件人地址
	private String consignee_postcode;	//收件人编码
	private String consignee_mobile;	//收件人手机号
	private Timestamp create_time;		//订单创建时间
	private Timestamp modified_time;	//订单修改时间
	private Timestamp end_time;			//订单结束时间
	private Timestamp pay_time;			//订单付款时间
	private Timestamp send_time;		//订单发货时间
	private Timestamp time_out_time;	//超时到期时间
	private BigInteger order_amount;	//订单金额
	private BigInteger discount_amount;	//优惠金额
	private BigInteger pay_amount;		//实付金额
	private BigInteger trans_exp;		//运费
	private int is_return;				//是否能退换货	0不能 1能
	private int coupon_id;				//优惠券ID
	private String remark;				//备注
	private String buyer_msg;			//买家留言
	private String buy_note;			//买家备注 只有买家自己可以看到
	private String seller_note;			//卖家备注 只有卖家自己可以看到
	private String buyer_nick;			//买家昵称
	private String seller_nick;			//卖家昵称
	private int can_rate;				//是否可以评价 0：是 1:否
	private String seller_cell;			//卖家手机
	private String seller_phone;		//卖家电话
	private String seller_name;			//卖家姓名
	private int is_logis;				//是否需要物流 0:有物流 1:无需物流
	private int is_bill;				//是否需要发票 0：不需要 1：需要
	private int is_delete;				//删除状态    0 删除  , 1 正常
	private String payway;				//支付方式
	private String payway_name;			//支付方式名称
	private int is_send;			//是否发货 0：未发货 1已发货
	private int is_change_price;	//是否修改过价格 0：未发货 1已发货
	public OrderNode() {
		super();
	}
	public BigInteger getOrder_id() {
		return order_id;
	}
	public void setOrder_id(BigInteger order_id) {
		this.order_id = order_id;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public String getPay_number() {
		return pay_number;
	}
	public void setPay_number(String pay_number) {
		this.pay_number = pay_number;
	}
	public int getOrder_state() {
		return order_state;
	}
	public void setOrder_state(int order_state) {
		this.order_state = order_state;
	}
	public int getPay_state() {
		return pay_state;
	}
	public void setPay_state(int pay_state) {
		this.pay_state = pay_state;
	}
	public int getOrder_type() {
		return order_type;
	}
	public void setOrder_type(int order_type) {
		this.order_type = order_type;
	}
	public BigInteger getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(BigInteger seller_id) {
		this.seller_id = seller_id;
	}
	public BigInteger getAccount_id() {
		return account_id;
	}
	public void setAccount_id(BigInteger account_id) {
		this.account_id = account_id;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getConsignee_area() {
		return consignee_area;
	}
	public void setConsignee_area(String consignee_area) {
		this.consignee_area = consignee_area;
	}
	public String getConsignee_address() {
		return consignee_address;
	}
	public void setConsignee_address(String consignee_address) {
		this.consignee_address = consignee_address;
	}
	public String getConsignee_postcode() {
		return consignee_postcode;
	}
	public void setConsignee_postcode(String consignee_postcode) {
		this.consignee_postcode = consignee_postcode;
	}
	public String getConsignee_mobile() {
		return consignee_mobile;
	}
	public void setConsignee_mobile(String consignee_mobile) {
		this.consignee_mobile = consignee_mobile;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public Timestamp getModified_time() {
		return modified_time;
	}
	public void setModified_time(Timestamp modified_time) {
		this.modified_time = modified_time;
	}
	public Timestamp getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}
	public Timestamp getPay_time() {
		return pay_time;
	}
	public void setPay_time(Timestamp pay_time) {
		this.pay_time = pay_time;
	}
	public Timestamp getSend_time() {
		return send_time;
	}
	public void setSend_time(Timestamp send_time) {
		this.send_time = send_time;
	}
	public Timestamp getTime_out_time() {
		return time_out_time;
	}
	public void setTime_out_time(Timestamp time_out_time) {
		this.time_out_time = time_out_time;
	}
	public BigInteger getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(BigInteger order_amount) {
		this.order_amount = order_amount;
	}
	public BigInteger getDiscount_amount() {
		return discount_amount;
	}
	public void setDiscount_amount(BigInteger discount_amount) {
		this.discount_amount = discount_amount;
	}
	public BigInteger getPay_amount() {
		return pay_amount;
	}
	public void setPay_amount(BigInteger pay_amount) {
		this.pay_amount = pay_amount;
	}
	public BigInteger getTrans_exp() {
		return trans_exp;
	}
	public void setTrans_exp(BigInteger trans_exp) {
		this.trans_exp = trans_exp;
	}
	public int getIs_return() {
		return is_return;
	}
	public void setIs_return(int is_return) {
		this.is_return = is_return;
	}
	public int getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(int coupon_id) {
		this.coupon_id = coupon_id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBuyer_msg() {
		return buyer_msg;
	}
	public void setBuyer_msg(String buyer_msg) {
		this.buyer_msg = buyer_msg;
	}
	public String getBuy_note() {
		return buy_note;
	}
	public void setBuy_note(String buy_note) {
		this.buy_note = buy_note;
	}
	public String getSeller_note() {
		return seller_note;
	}
	public void setSeller_note(String seller_note) {
		this.seller_note = seller_note;
	}
	public String getBuyer_nick() {
		return buyer_nick;
	}
	public void setBuyer_nick(String buyer_nick) {
		this.buyer_nick = buyer_nick;
	}
	public String getSeller_nick() {
		return seller_nick;
	}
	public void setSeller_nick(String seller_nick) {
		this.seller_nick = seller_nick;
	}
	public int getCan_rate() {
		return can_rate;
	}
	public void setCan_rate(int can_rate) {
		this.can_rate = can_rate;
	}
	public String getSeller_cell() {
		return seller_cell;
	}
	public void setSeller_cell(String seller_cell) {
		this.seller_cell = seller_cell;
	}
	public String getSeller_phone() {
		return seller_phone;
	}
	public void setSeller_phone(String seller_phone) {
		this.seller_phone = seller_phone;
	}
	public String getSeller_name() {
		return seller_name;
	}
	public void setSeller_name(String seller_name) {
		this.seller_name = seller_name;
	}
	public int getIs_logis() {
		return is_logis;
	}
	public void setIs_logis(int is_logis) {
		this.is_logis = is_logis;
	}
	public int getIs_bill() {
		return is_bill;
	}
	public void setIs_bill(int is_bill) {
		this.is_bill = is_bill;
	}
	public int getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public String getPayway() {
		return payway;
	}
	public void setPayway(String payway) {
		this.payway = payway;
	}
	public String getPayway_name() {
		return payway_name;
	}
	public void setPayway_name(String payway_name) {
		this.payway_name = payway_name;
	}
	public int getIs_send() {
		return is_send;
	}
	public void setIs_send(int is_send) {
		this.is_send = is_send;
	}
	public int getIs_change_price() {
		return is_change_price;
	}
	public void setIs_change_price(int is_change_price) {
		this.is_change_price = is_change_price;
	}

}
