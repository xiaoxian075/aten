package com.aten.service;

import com.aten.model.orm.CatAppraisal;

public  interface CatAppraisalService extends CommonService<CatAppraisal, String>{


    void deleteCatAppraisal(String cat_id);
}

