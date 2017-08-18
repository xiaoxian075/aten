package com.aten.service.impl;

import java.util.HashMap;
import java.util.List;

import com.aten.model.orm.Sysmenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.SysmenuDao;
import com.aten.service.SysmenuService;

@Service("sysmenuService")
public class SysmenuServiceImpl extends CommonServiceImpl<Sysmenu,String> implements SysmenuService{

	private SysmenuDao sysmenuDao;
	
	@Autowired
	public SysmenuServiceImpl(SysmenuDao sysmenuDao) {
		super(sysmenuDao);
		this.sysmenuDao = sysmenuDao;
	}

	public List<Sysmenu> getSysmenuListByState(HashMap<String, Object> paraMap) {
		paraMap.put("is_show", "1");
		return sysmenuDao.getList(paraMap);
	}
	
	
	public List<Sysmenu> getSysmenuListByManageState(HashMap<String, Object> paraMap) {
		return getSysmenuListByState(paraMap);
	}


	
}




