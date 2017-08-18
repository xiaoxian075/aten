package com.aten.dao;

import com.aten.model.orm.BillFlow;

public interface BillFlowDao  extends CommonDao<BillFlow, String>{

	void insert(BillFlow billFlow);

}
