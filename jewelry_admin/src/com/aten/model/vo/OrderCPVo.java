package com.aten.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class OrderCPVo implements Serializable {
	private static final long serialVersionUID = -8729361041462602252L;

	private BigInteger order_id;
	private BigDecimal trans_exp;
	private List<OrderDetailCPVo> arr_detail;
	public BigInteger getOrder_id() {
		return order_id;
	}
	public void setOrder_id(BigInteger order_id) {
		this.order_id = order_id;
	}
	public BigDecimal getTrans_exp() {
		return trans_exp;
	}
	public void setTrans_exp(BigDecimal trans_exp) {
		this.trans_exp = trans_exp;
	}
	public List<OrderDetailCPVo> getArr_detail() {
		return arr_detail;
	}
	public void setArr_detail(List<OrderDetailCPVo> arr_detail) {
		this.arr_detail = arr_detail;
	}
	
	public BigInteger getIntTrans_exp() {
		return new BigInteger(trans_exp.multiply(new BigDecimal("100")).setScale(0,BigDecimal.ROUND_HALF_UP).toString());
	}
}
