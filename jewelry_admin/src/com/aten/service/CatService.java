package com.aten.service;

import com.aten.model.orm.Cat;
import com.communal.util.Query;

import java.util.HashMap;
import java.util.List;

public interface CatService extends CommonService<Cat, String> {
	List<Cat> selectCatByPid(String pid);

	List<Cat> getSon(String pId);

	void limitState(String parameter_id);

	void batchLimitState(String parameter_id);

	void enableState(String parameter_id);

	void batchEnableState(String parameter_id);

	List<Cat> getListAndDivideRate(HashMap<String, Object> paraMap);

	List<Cat> queryList(Query query);

	List<Cat> lastLevelList(Query query);

	int lastLevelListCount(Query query);

	void deletePre(String parameter_id);

	Boolean isSon(String cat_id, String parent_id);

	void setLevel(String cat_id);

	void deleteGoodsClass(String parameter_id);

	String deleteNewsClass(String parameter_id);

	List<Cat> findGoodscats(String parent_cat_id);
	
	List<Cat> findGoodsCatsByOne(String parent_cat_id);

	Cat getWithRate(String cat_id);

	// Boolean isSon(String level_cat,String parent_id);
}
