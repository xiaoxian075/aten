package com.aten.dao;

import java.util.HashMap;
import java.util.List;

import com.aten.model.orm.Commpara;


public interface CommparaDao extends CommonDao<Commpara, String>{
	
	/**
	 * @author linjunqin
	 * @Description 获取字典编码的列表
	 * @param
	 * @date 2017-7-3 下午5:17:16
	 */
	public List<Commpara> getParaCodeList(HashMap<String, String> paraMap);
	
}


