package com.aten.service;

import com.aten.model.orm.AccountInterApprove;

public  interface AccountInterApproveService extends CommonService<AccountInterApprove, String>{

	void approve(AccountInterApprove accountInterApprove);
	

	
}

