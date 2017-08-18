package com.aten.service;

import com.aten.model.orm.CatBrand;

public  interface CatBrandService extends CommonService<CatBrand, String>{


    void deleteCatBrand(String cat_id);
}

