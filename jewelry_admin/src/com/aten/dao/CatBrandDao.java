package com.aten.dao;
import com.aten.model.orm.CatBrand;

public interface CatBrandDao extends CommonDao<CatBrand, String>{

    void deleteCatBrand(String cat_id);
}


