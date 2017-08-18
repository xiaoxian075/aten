package com.aten.service;

import com.aten.model.orm.AccountBalanceApprove;

public  interface AccountBalanceApproveService extends CommonService<AccountBalanceApprove, String>{

	void approve(AccountBalanceApprove accountBalanceApprove);
	

	
}

