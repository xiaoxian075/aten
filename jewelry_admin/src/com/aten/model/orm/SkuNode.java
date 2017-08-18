package com.aten.model.orm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class SkuNode implements Serializable{
	private static final long serialVersionUID = -6764716729748814369L;
	private BigInteger sku_id;
	private BigInteger goods_id;
	private String sku_name;
	private String sku_str;
	private BigDecimal price;
	private BigDecimal manual_fee;
	private BigDecimal sale_price;
	private int stock;
	public SkuNode() {
		super();
	}
	public SkuNode(BigInteger sku_id, BigInteger goods_id, String sku_name, String sku_str, BigDecimal price,
			BigDecimal manual_fee, BigDecimal sale_price, int stock) {
		super();
		this.sku_id = sku_id;
		this.goods_id = goods_id;
		this.sku_name = sku_name;
		this.sku_str = sku_str;
		this.price = price;
		this.manual_fee = manual_fee;
		this.sale_price = sale_price;
		this.stock = stock;
	}
	public BigInteger getSku_id() {
		return sku_id;
	}
	public void setSku_id(BigInteger sku_id) {
		this.sku_id = sku_id;
	}
	public BigInteger getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(BigInteger goods_id) {
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
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getManual_fee() {
		return manual_fee;
	}
	public void setManual_fee(BigDecimal manual_fee) {
		this.manual_fee = manual_fee;
	}
	public BigDecimal getSale_price() {
		return sale_price;
	}
	public void setSale_price(BigDecimal sale_price) {
		this.sale_price = sale_price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
}
//	CREATE TABLE `t_sku` (
//			  `sku_id` bigint(20) NOT NULL AUTO_INCREMENT,
//			  `goods_id` bigint(20) NOT NULL,
//			  `sku_name` varchar(200) DEFAULT NULL COMMENT 'name,name,name',
//			  `sku_str` varchar(100) DEFAULT NULL COMMENT 'id,id,id   补位存储',
//			  `price` decimal(15,0) DEFAULT NULL,
//			  `manual_fee` decimal(15,0) DEFAULT NULL,
//			  `sale_price` int(11) NOT NULL,
//			  `stock` int(11) NOT NULL,
//			  PRIMARY KEY (`sku_id`)
//			) ENGINE=InnoDB AUTO_INCREMENT=215 DEFAULT CHARSET=utf8;

