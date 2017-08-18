package com.aten.service.impl;

import com.aten.model.orm.AccountBill;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.AccountBillDao;
import com.aten.service.AccountBillService;
import com.communal.util.Query;

@Service("accountBillService")
public class AccountBillServiceImpl extends CommonServiceImpl<AccountBill,String> implements AccountBillService{

	private AccountBillDao accountBillDao;

	@Autowired
	public AccountBillServiceImpl(AccountBillDao accountBillDao) {
		super(accountBillDao);
		this.accountBillDao=accountBillDao;
	}

	@Override
	public List<AccountBill> queryList(Query query) {
		// TODO Auto-generated method stub
		return accountBillDao.queryList(query);
	}

}




