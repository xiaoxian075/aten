package com.aten.model.orm;

import java.io.Serializable;
import java.math.BigInteger;

public class ManaFunds implements Serializable {
	private static final long serialVersionUID = 366689224636881030L;

	private BigInteger fund_id;
	private BigInteger seller_id;	//卖家标识
	private BigInteger total_assets;	//总资产
	private BigInteger balance;		//可用余额
	private BigInteger frozen_amount;	//冻结金额
	public ManaFunds() {
		super();
	}
	public ManaFunds(BigInteger fund_id, BigInteger seller_id, BigInteger total_assets, BigInteger balance,
			BigInteger frozen_amount) {
		super();
		this.fund_id = fund_id;
		this.seller_id = seller_id;
		this.total_assets = total_assets;
		this.balance = balance;
		this.frozen_amount = frozen_amount;
	}
	public BigInteger getFund_id() {
		return fund_id;
	}
	public void setFund_id(BigInteger fund_id) {
		this.fund_id = fund_id;
	}
	public BigInteger getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(BigInteger seller_id) {
		this.seller_id = seller_id;
	}
	public BigInteger getTotal_assets() {
		return total_assets;
	}
	public void setTotal_assets(BigInteger total_assets) {
		this.total_assets = total_assets;
	}
	public BigInteger getBalance() {
		return balance;
	}
	public void setBalance(BigInteger balance) {
		this.balance = balance;
	}
	public BigInteger getFrozen_amount() {
		return frozen_amount;
	}
	public void setFrozen_amount(BigInteger frozen_amount) {
		this.frozen_amount = frozen_amount;
	}
}
