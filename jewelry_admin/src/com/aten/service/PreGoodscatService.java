package com.aten.service;

import com.aten.model.orm.PreGoodscat;

public  interface PreGoodscatService extends CommonService<PreGoodscat, String>{


    void deletePreCat(String precat_id);
}

