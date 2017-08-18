package com.aten.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class OrderDetailCPVo implements Serializable {
	private static final long serialVersionUID = -2297552314146137283L;

	private BigInteger detail_id;
	private BigDecimal total_amount;
	public BigInteger getDetail_id() {
		return detail_id;
	}
	public void setDetail_id(BigInteger detail_id) {
		this.detail_id = detail_id;
	}
	public BigDecimal getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}
	
	public BigInteger getIntTotal_amount() {
		return new BigInteger(total_amount.multiply(new BigDecimal("100")).setScale(0,BigDecimal.ROUND_HALF_UP).toString());
	}
}
