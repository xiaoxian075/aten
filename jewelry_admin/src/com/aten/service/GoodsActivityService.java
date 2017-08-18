package com.aten.service;

import com.aten.model.orm.GoodsActivity;

public  interface GoodsActivityService extends CommonService<GoodsActivity, String>{


    void saveInfo(GoodsActivity goodsActivity);

    void updateInfo(GoodsActivity goodsActivity);

    void delete(String activity_id);

    void enableState(GoodsActivity goodsActivity);

    void limitState(GoodsActivity goodsActivity);
}

