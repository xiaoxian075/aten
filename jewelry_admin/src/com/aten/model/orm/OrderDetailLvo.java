package com.aten.model.orm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class OrderDetailLvo implements Serializable{
	private static final long serialVersionUID = 7895065461689995564L;
	
	private BigInteger detail_id;
	private String detail_number;		//子订单编号
	private BigInteger goods_id;		//商品ID
	private String goods_name;			//商品名称
	private String goods_img;			//商品图片
	private BigInteger sale_price;		//单价
	private int num;					//商品数量
	public OrderDetailLvo() {
		super();
	}
	public OrderDetailLvo(OrderDetailNode info) {
		this.detail_id = info.getDetail_id();
		this.detail_number = info.getDetail_number();
		this.goods_id = info.getGoods_id();
		this.goods_name = info.getGoods_name();
		this.goods_img = info.getGoods_img();
		this.sale_price = info.getSale_price();
		this.num = info.getNum();
	}
	public BigInteger getDetail_id() {
		return detail_id;
	}
	public void setDetail_id(BigInteger detail_id) {
		this.detail_id = detail_id;
	}
	public String getDetail_number() {
		return detail_number;
	}
	public void setDetail_number(String detail_number) {
		this.detail_number = detail_number;
	}
	public BigInteger getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(BigInteger goods_id) {
		this.goods_id = goods_id;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getGoods_img() {
		return goods_img;
	}
	public void setGoods_img(String goods_img) {
		this.goods_img = goods_img;
	}
	public BigInteger getSale_price() {
		return sale_price;
	}
	public void setSale_price(BigInteger sale_price) {
		this.sale_price = sale_price;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
}
