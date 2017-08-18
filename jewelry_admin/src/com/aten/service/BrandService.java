package com.aten.service;

import com.aten.model.orm.Brand;
import com.communal.util.Query;

import java.util.List;

public  interface BrandService extends CommonService<Brand, String>{


    List<Brand> queryList(Query query);
    List<Brand>selectByCatId(String catId);
}

