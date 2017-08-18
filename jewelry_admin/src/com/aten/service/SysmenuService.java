package com.aten.service;

import java.util.HashMap;
import java.util.List;

import com.aten.model.orm.Sysmenu;

public  interface SysmenuService extends CommonService<Sysmenu, String>{
	
	public List<Sysmenu> getSysmenuListByState(HashMap<String, Object> paraMap);
	
	public List<Sysmenu> getSysmenuListByManageState(HashMap<String, Object> paraMap);
	
	
}

