package com.aten.service.impl;

import com.aten.model.orm.AccountCoupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.AccountCouponDao;
import com.aten.service.AccountCouponService;

@Service("accountCouponService")
public class AccountCouponServiceImpl extends CommonServiceImpl<AccountCoupon,String> implements AccountCouponService{

	private AccountCouponDao accountCouponDao;

	@Autowired
	public AccountCouponServiceImpl(AccountCouponDao accountCouponDao) {
		super(accountCouponDao);
		this.accountCouponDao=accountCouponDao;
	}

}




