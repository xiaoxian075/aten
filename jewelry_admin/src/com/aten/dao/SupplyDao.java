package com.aten.dao;
import java.util.List;
import java.util.Map;

import com.aten.model.orm.Supply;
import com.communal.util.Query;

public interface SupplyDao extends CommonDao<Supply, String>{

	int updateStatus(Map<String,Object> param);

    List<Supply> queryList(Query query);
    List<Supply>selectByCatId(String catId);
}


