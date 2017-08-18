package com.aten.dao;
import java.util.List;

import com.aten.model.orm.AccountBill;
import com.communal.util.Query;

public interface AccountBillDao extends CommonDao<AccountBill, String>{

	List<AccountBill> queryList(Query query);
	
}


