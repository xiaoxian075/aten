package com.aten.dao;
import com.aten.model.orm.Appraisal;
import com.communal.util.Query;

import java.util.List;

public interface AppraisalDao extends CommonDao<Appraisal, String>{

    List<Appraisal> queryList(Query query);
    List<Appraisal>selectByCatId(String catId);
}


