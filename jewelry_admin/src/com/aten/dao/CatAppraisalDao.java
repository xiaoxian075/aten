package com.aten.dao;
import com.aten.model.orm.CatAppraisal;

public interface CatAppraisalDao extends CommonDao<CatAppraisal, String>{

    void deleteCatAppraisal(String cat_id);
}


