package com.aten.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aten.model.orm.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import com.aten.dao.CommonDao;
import com.aten.service.CommonService;

public  class CommonServiceImpl<T, PK> implements CommonService<T, PK>{
	
	private CommonDao<T, PK> commonDao;

	@Autowired
	public CommonServiceImpl(CommonDao<T, PK> commonDao) {
		this.commonDao =  commonDao;
	}

	public void insert(T t) {
		this.commonDao.insert(t);
	}

	public int insertGetPk(T t) {
		return this.commonDao.insertGetPk(t);
	}
	
	public void update(T t) {
		this.commonDao.update(t);
	}

	public void deleteOne(String id){
		String[] ids = id.split(",");
		this.commonDao.delete(ids);
	}

	public void delete(String[] ids) {
		this.commonDao.delete(ids);
	}

	public int getCount(Map<String, Object> paraMap) {
		return this.commonDao.getCount(paraMap);
	}
	
	public List<T> getList(Map<String, Object> paraMap) {
		return this.commonDao.getList(paraMap);
	}

	public void updateBatch(List<Map<String, String>> mapList) {
		this.commonDao.updateBatch(mapList);
	}

	public T get(PK id) {
		return this.commonDao.get(id);
	}

	public List<HashMap<String, Object>> getObjMapList(
			Map<String, String> paraMap) {
		return this.commonDao.getObjMapList(paraMap);
	}

	public T get(HashMap<String, String> paraMap) {
		return this.commonDao.get(paraMap);
	}

	
}
