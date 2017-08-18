package com.aten.service;

import java.util.List;

import com.aten.model.orm.AccountBill;
import com.communal.util.Query;

public  interface AccountBillService extends CommonService<AccountBill, String>{

	List<AccountBill> queryList(Query query);
	

	
}

