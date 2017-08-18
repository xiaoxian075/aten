package com.aten.dao;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.aten.model.orm.Account;
import com.aten.model.orm.AccountWithdrawBill;

public interface AccountDao{

	Account getByLoginname(String login_name);

    void updateAccountEarningsReduce(AccountWithdrawBill accountWithdrawBill);

	void updateBalance(HashMap<String, Object> paraMap);

	Account getById(BigInteger id);

	int updateoneBalanceById(Map<String, Object> paraMap);
	
	void updateIntegral(HashMap<String, Object> paraMap);
}
