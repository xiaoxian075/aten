package com.aten.service;

import com.aten.model.orm.Appraisal;
import com.communal.util.Query;

import java.util.List;

public  interface AppraisalService extends CommonService<Appraisal, String>{


    List<Appraisal> queryList(Query query);
    List<Appraisal>selectByCatId(String catId);
}

