package com.aten.service;

import com.aten.model.orm.CatSupply;

public  interface CatSupplyService extends CommonService<CatSupply, String>{


    void deleteCatSupply(String cat_id);

}

