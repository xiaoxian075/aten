package com.aten.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.aten.model.orm.Area;
import com.aten.model.orm.Commpara;
import com.aten.dao.AreaDao;
import com.aten.dao.CommonDao;
import com.aten.dao.CommparaDao;
import com.aten.service.AreaService;
import com.aten.service.impl.CommonServiceImpl;

public class BaseCommonServiceImpl<T, PK> extends CommonServiceImpl<T,PK>{
	
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private CommparaDao commparaDao;
	
	@Autowired
	public BaseCommonServiceImpl(CommonDao<T, PK> commonDao) {
		super(commonDao);
	}

	/**
	 * @author linjunqin
	 * @Description 获取地区状态为显示的正常数据
	 * @param
	 * @date 2017-7-3 下午2:40:27
	 */
	 public List<Area> getAreaListByIsState(HashMap<String, Object> paraMap){
		 if(paraMap==null) paraMap = new HashMap<String,Object>();
		 paraMap.put("state", "1");
		 List<Area> areaList = areaDao.getList(paraMap);
		 return areaList;
	 }

	 /**
	 * @author linjunqin
	 * @Description 获取数据字典的列表
	 * @param
	 * @date 2017-7-3 下午5:07:19
	 */
	 public List<Commpara> getCommparaListByIsState(HashMap<String, Object> paraMap){
		 if(paraMap==null) paraMap = new HashMap<String,Object>();
		 paraMap.put("state", "1");
		 List<Commpara> commparaList = commparaDao.getList(paraMap);
		 return commparaList;
	 }
	 
	 
	
}
