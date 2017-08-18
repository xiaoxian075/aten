package com.aten.service;

import com.aten.model.orm.Brand;
import com.aten.model.orm.Supply;
import com.communal.util.Query;

import java.util.List;

public  interface SupplyService extends CommonService<Supply, String>{

	int updateStatus(String supply_id, String status);


    List<Supply> queryList(Query query);
    List<Supply>selectByCatId(String catId);
}

