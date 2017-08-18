package com.aten.dao;
import com.aten.model.orm.Brand;
import com.communal.util.Query;

import java.util.List;

public interface BrandDao extends CommonDao<Brand, String>{

    List<Brand> queryList(Query query);
    
    List<Brand>selectByCatId(String catId);
}


