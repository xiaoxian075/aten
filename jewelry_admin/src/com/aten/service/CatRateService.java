package com.aten.service;

import com.aten.model.orm.CatRate;

import java.util.List;

public  interface CatRateService extends CommonService<CatRate, String>{


    List<CatRate> getByCatId(String cat_id);

}

