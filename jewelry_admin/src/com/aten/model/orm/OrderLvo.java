package com.aten.model.orm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

public class OrderLvo implements Serializable{
	
	private static final long serialVersionUID = -1415985741540617006L;
	private BigInteger order_id;
	private String order_number;	//订单编号
	private BigInteger account_id;	//帐户ID
	private int order_state;		//订单状态
									/*	            0：未付款（等待买家付款）
										            1：等待卖家发货(买家已付款)
										            2：等待买家确认收货（卖家已发货）
										            3：交易成功
										            4： 退款中
										            5： 退款成功（交易关闭）
										            6： 未付款前，关闭交易',
									*/	   
	private int order_type;			//订单类型  1 正常订单  2 预付订单
	private BigInteger order_amount;	//订单金额
	private BigInteger discount_amount;	//优惠金额
	private BigInteger pay_amount;		//实付金额
	private BigInteger trans_exp;		//运费
	private int is_bill;				//是否需要发票 0：不需要 1：需要
	private int model;			//预售模式 1 全额 ， 2 定金
	private BigDecimal book_amount;		//预售金额
	private BigDecimal return_amount;	//尾款金额
	private Timestamp create_time;		//订单创建时间
	private List<OrderDetailLvo> listDetail;
	
	
	public OrderLvo() {
		super();
	}
	public OrderLvo(OrderNode order, OrderBookNode orderBook, List<OrderDetailLvo> listDetail) {
		this.order_id = order.getOrder_id();
		this.order_number = order.getOrder_number();
		this.account_id = order.getAccount_id();
		this.order_state = order.getOrder_state();	   
		this.order_type = order.getOrder_type();
		this.order_amount = order.getOrder_amount();
		this.discount_amount = order.getDiscount_amount();
		this.pay_amount = order.getPay_amount();
		this.trans_exp = order.getTrans_exp();
		this.is_bill = order.getIs_bill();
		this.model = orderBook.getModel();
		this.book_amount = orderBook.getBook_amount();
		this.return_amount = orderBook.getReturn_amount();
		this.create_time = order.getCreate_time();
		this.listDetail = listDetail;
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
	public BigInteger getAccount_id() {
		return account_id;
	}
	public void setAccount_id(BigInteger account_id) {
		this.account_id = account_id;
	}
	public int getOrder_state() {
		return order_state;
	}
	public void setOrder_state(int order_state) {
		this.order_state = order_state;
	}
	public int getOrder_type() {
		return order_type;
	}
	public void setOrder_type(int order_type) {
		this.order_type = order_type;
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
	public int getIs_bill() {
		return is_bill;
	}
	public void setIs_bill(int is_bill) {
		this.is_bill = is_bill;
	}
	public int getModel() {
		return model;
	}
	public void setModel(int model) {
		this.model = model;
	}
	public BigDecimal getBook_amount() {
		return book_amount;
	}
	public void setBook_amount(BigDecimal book_amount) {
		this.book_amount = book_amount;
	}
	public BigDecimal getReturn_amount() {
		return return_amount;
	}
	public void setReturn_amount(BigDecimal return_amount) {
		this.return_amount = return_amount;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public List<OrderDetailLvo> getListDetail() {
		return listDetail;
	}
	public void setListDetail(List<OrderDetailLvo> listDetail) {
		this.listDetail = listDetail;
	}
}
