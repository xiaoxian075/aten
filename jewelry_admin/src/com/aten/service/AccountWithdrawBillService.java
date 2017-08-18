package com.aten.service;

import com.aten.model.orm.AccountWithdrawBill;

import java.util.HashMap;

public  interface AccountWithdrawBillService extends CommonService<AccountWithdrawBill, String>{


    void audit(AccountWithdrawBill accountWithdrawBill);

    AccountWithdrawBill getTotalAmount(HashMap<String, Object> paraMap);
}

