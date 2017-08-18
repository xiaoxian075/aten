package com.aten.dao;
import com.aten.model.orm.GoodsActivityMap;

import java.util.List;

public interface GoodsActivityMapDao extends CommonDao<GoodsActivityMap, String> {

    void deleteGoodsActivity(String activity_id);

    List<GoodsActivityMap> findAll(String activity_id);

    List<GoodsActivityMap>  findByActivityId(String activity_id);
}


