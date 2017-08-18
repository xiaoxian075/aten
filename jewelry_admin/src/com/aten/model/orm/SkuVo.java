package com.aten.model.orm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class SkuVo implements Serializable{
	private static final long serialVersionUID = 6104990264935169030L;
	
	private BigInteger sku_id;
	private String attr_id;
	private String price;
	private String manual_fee;
	private String sale_price;
	private int stock;
	private BigInteger goods_id;
	public SkuVo() {
		super();
	}
	public SkuVo(BigInteger sku_id, BigInteger goods_id,String attr_id, String price, String manual_fee, String sale_price,
			int stock) {
		super();
		this.sku_id = sku_id;
		this.goods_id = goods_id;
		this.attr_id = attr_id;
		this.price = price;
		this.manual_fee = manual_fee;
		this.sale_price = sale_price;
		this.stock = stock;
	}
	
	
	public BigInteger getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(BigInteger goods_id) {
		this.goods_id = goods_id;
	}
	public BigInteger getSku_id() {
		return sku_id;
	}
	public void setSku_id(BigInteger sku_id) {
		this.sku_id = sku_id;
	}
	public String getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getManual_fee() {
		return manual_fee;
	}
	public void setManual_fee(String manual_fee) {
		this.manual_fee = manual_fee;
	}
	public String getSale_price() {
		return sale_price;
	}
	public void setSale_price(String sale_price) {
		this.sale_price = sale_price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
}
