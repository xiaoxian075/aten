package com.aten.service;

import java.util.HashMap;
import java.util.List;
import com.aten.model.orm.Commpara;

public  interface CommparaService extends CommonService<Commpara, String>{
	
	
	 public List<Commpara> getCommparaListByPara(String paraStr);
	
	
	 /**
	 * @author linjunqin
	 * @Description 获取数据字典的列表
	 * @param
	 * @date 2017-7-3 下午5:07:19
	 */
	 public List<Commpara> getCommparaListByIsState(HashMap<String, Object> paraMap);
	 
	 
	 /**
	  * @author linjunqin
	  * @Description 获取字典编码的列表
	  * @param
	  * @date 2017-7-3 下午5:17:16
	  */
	 public List<Commpara> getParaCodeList(HashMap<String, String> paraMap);
}

