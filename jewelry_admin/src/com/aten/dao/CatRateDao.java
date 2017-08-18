package com.aten.dao;
import com.aten.model.orm.CatRate;

import java.util.List;

public interface CatRateDao extends CommonDao<CatRate, String>{

    List<CatRate> getByCatId(String cat_id);
}


