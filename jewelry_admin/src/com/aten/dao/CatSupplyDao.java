package com.aten.dao;
import com.aten.model.orm.CatSupply;

public interface CatSupplyDao extends CommonDao<CatSupply, String>{

    void deleteCatSupply(String cat_id);
}


