package com.aten.model.orm;

import java.io.Serializable;
import java.math.BigInteger;

public class CustomAttrNode implements Serializable{
	private static final long serialVersionUID = -4151772595723751161L;
	private BigInteger custom_attr_id;
	private String custom_alias;
	private BigInteger goods_id;
	private BigInteger attr_id;
	private int attr_type;
	public BigInteger getCustom_attr_id() {
		return custom_attr_id;
	}
	public void setCustom_attr_id(BigInteger custom_attr_id) {
		this.custom_attr_id = custom_attr_id;
	}
	public String getCustom_alias() {
		return custom_alias;
	}
	public void setCustom_alias(String custom_alias) {
		this.custom_alias = custom_alias;
	}
	public BigInteger getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(BigInteger goods_id) {
		this.goods_id = goods_id;
	}
	public BigInteger getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(BigInteger attr_id) {
		this.attr_id = attr_id;
	}
	public int getAttr_type() {
		return attr_type;
	}
	public void setAttr_type(int attr_type) {
		this.attr_type = attr_type;
	}
}
