package com.aten.dao;
import com.aten.model.orm.PreGoodscat;

public interface PreGoodscatDao extends CommonDao<PreGoodscat, String>{

    void deletePreCat(String precat_id);
}


