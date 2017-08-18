package com.aten.service.impl;

import com.aten.model.orm.ManaBills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.ManaBillsDao;
import com.aten.service.ManaBillsService;

@Service("manaBillsService")
public class ManaBillsServiceImpl extends CommonServiceImpl<ManaBills,String> implements ManaBillsService{

	private ManaBillsDao manaBillsDao;

	@Autowired
	public ManaBillsServiceImpl(ManaBillsDao manaBillsDao) {
		super(manaBillsDao);
		this.manaBillsDao=manaBillsDao;
	}

}




