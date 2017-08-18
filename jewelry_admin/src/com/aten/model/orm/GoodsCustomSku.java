package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class GoodsCustomSku implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String sku_id;
	private String goods_id;
	private String sku_name;
	private String sku_str;
	private String price;
	private String manual_fee;
	private String sale_price;
	private int stock;
	private String attr_value;
	private String attr_value_id;
	
	
	
	
	public String getAttr_value_id() {
		return attr_value_id;
	}
	public void setAttr_value_id(String attr_value_id) {
		this.attr_value_id = attr_value_id;
	}
	public String getAttr_value() {
		return attr_value;
	}
	public void setAttr_value(String attr_value) {
		this.attr_value = attr_value;
	}
	public String getSku_id() {
		return sku_id;
	}
	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
	}
	
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	
	public String getSku_name() {
		return sku_name;
	}
	public void setSku_name(String sku_name) {
		this.sku_name = sku_name;
	}
	
	public String getSku_str() {
		return sku_str;
	}
	public void setSku_str(String sku_str) {
		this.sku_str = sku_str;
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

