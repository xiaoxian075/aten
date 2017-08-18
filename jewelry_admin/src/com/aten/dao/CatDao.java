package com.aten.dao;
import com.aten.model.orm.Cat;
import com.communal.util.Query;

import java.util.HashMap;
import java.util.List;

public interface CatDao extends CommonDao<Cat, String>{
	
    List<Cat> selectCatByPid(String pid);

    List<Cat> getSon(String pId);

    List<Cat> getListAndDivideRate(HashMap<String, Object> paraMap);

    List<Cat> queryList(Query query);

    List<Cat> lastLevelList(Query query);

    int lastLevelListCount(Query query);

    List<Cat> findGoodscats(String parent_cat_id);
    
    List<Cat> findGoodsCatsByOne(String parent_cat_id);

	Cat getWithRate(String cat_id);
}


