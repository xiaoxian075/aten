package com.aten.service;

import java.math.BigInteger;

import com.aten.model.orm.Account;
import com.aten.model.orm.AccountWithdrawBill;

public interface AccountService{
	
	Account getById(BigInteger id);

	Account getByLoginname(String login_name);

    void updateAccountEarningsReduce(AccountWithdrawBill accountWithdrawBill);
}
