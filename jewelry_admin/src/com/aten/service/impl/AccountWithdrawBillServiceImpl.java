package com.aten.service.impl;

import com.aten.model.orm.Account;
import com.aten.model.orm.AccountWithdrawBill;
import com.aten.service.AccountService;
import com.communal.constants.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.AccountWithdrawBillDao;
import com.aten.service.AccountWithdrawBillService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Service("accountWithdrawBillService")
@Transactional
public class AccountWithdrawBillServiceImpl extends CommonServiceImpl<AccountWithdrawBill,String> implements AccountWithdrawBillService{

	private AccountWithdrawBillDao accountWithdrawBillDao;
	@Autowired
	private AccountService accountService;
	@Autowired
	public AccountWithdrawBillServiceImpl(AccountWithdrawBillDao accountWithdrawBillDao) {
		super(accountWithdrawBillDao);
		this.accountWithdrawBillDao=accountWithdrawBillDao;
	}

	@Override
	public void audit(AccountWithdrawBill accountWithdrawBill) {
		AccountWithdrawBill AWB=get(accountWithdrawBill.getId());
		//如果不同意  还原acount中的收益
		if("2".equals(accountWithdrawBill.getAudit_state())){
			synchronized (AWB.getAccount_id().intern()){
				accountService.updateAccountEarningsReduce(AWB);
			}

		}

		update(accountWithdrawBill);
	}

	@Override
	public AccountWithdrawBill getTotalAmount(HashMap<String, Object> paraMap) {
		return accountWithdrawBillDao.getTotalAmount(paraMap);
	}
}




