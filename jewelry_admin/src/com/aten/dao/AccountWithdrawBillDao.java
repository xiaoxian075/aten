package com.aten.dao;
import com.aten.model.orm.AccountWithdrawBill;

import java.util.HashMap;

public interface AccountWithdrawBillDao extends CommonDao<AccountWithdrawBill, String>{

    AccountWithdrawBill getTotalAmount(HashMap<String, Object> paraMap);
}


