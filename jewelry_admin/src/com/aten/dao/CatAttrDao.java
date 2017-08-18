package com.aten.dao;
import com.aten.model.orm.CatAttr;

import java.util.List;

public interface CatAttrDao extends CommonDao<CatAttr, String>{

    void deleteCatSkuAttr(String cat_id);

    void deleteCatKeyAttr(String cat_id);

    List<CatAttr> getByCatId(String cat_id);

    List<CatAttr> findByAttrId(String attr_id);
}


