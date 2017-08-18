package com.aten.service.impl;

import java.util.HashMap;
import java.util.List;
import com.aten.model.orm.Commpara;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.CommparaDao;
import com.aten.service.CommparaService;

@Service("commparaService")
public class CommparaServiceImpl extends BaseCommonServiceImpl<Commpara,String> implements CommparaService{

	private CommparaDao commparaDao;
	
	@Autowired
	public CommparaServiceImpl(CommparaDao commparaDao) {
		super(commparaDao);
		this.commparaDao=commparaDao;
	}

	/**
	 * @author linjunqin
	 * @Description 获取数据字典的列表
	 * @param
	 * @date 2017-7-3 下午5:07:19
	 */
	public List<Commpara> getCommparaListByPara(String paraStr){
		HashMap<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("para_code",paraStr);
		return getCommparaListByIsState(paraMap);
	}

	/**
	  * @author linjunqin
	  * @Description 获取字典编码的列表
	  * @param
	  * @date 2017-7-3 下午5:17:16
	  */
	@Override
	public List<Commpara> getParaCodeList(HashMap<String, String> paraMap) {
		return commparaDao.getParaCodeList(paraMap);
	}


}




