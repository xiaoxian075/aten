package com.aten.service.impl;

import com.aten.model.orm.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.ManagerDao;
import com.aten.service.ManagerService;

@Service("managerService")
public class ManagerServiceImpl extends CommonServiceImpl<Manager,String> implements ManagerService{

	@Autowired
	public ManagerServiceImpl(ManagerDao managerDao) {
		super(managerDao);
	}

}




