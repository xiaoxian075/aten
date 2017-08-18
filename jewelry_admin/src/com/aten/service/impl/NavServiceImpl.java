package com.aten.service.impl;

import com.aten.model.orm.Nav;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.NavDao;
import com.aten.service.NavService;

@Service("navService")
public class NavServiceImpl extends CommonServiceImpl<Nav,String> implements NavService{

	@Autowired
	public NavServiceImpl(NavDao navDao) {
		super(navDao);
	}

}




