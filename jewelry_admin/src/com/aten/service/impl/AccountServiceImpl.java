package com.aten.service.impl;

import com.aten.model.orm.AccountWithdrawBill;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aten.dao.AccountDao;
import com.aten.model.orm.Account;
import com.aten.service.AccountService;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao dao;

	@Override
	public Account getByLoginname(String login_name) {
		return dao.getByLoginname(login_name);
	}

	@Override
	public void updateAccountEarningsReduce(AccountWithdrawBill accountWithdrawBill) {
		dao.updateAccountEarningsReduce(accountWithdrawBill);
	}

	@Override
	public Account getById(BigInteger id) {
		return dao.getById(id);
	}

}
