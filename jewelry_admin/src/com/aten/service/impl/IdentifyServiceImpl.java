package com.aten.service.impl;

import com.aten.model.orm.Identify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.IdentifyDao;
import com.aten.service.IdentifyService;

@Service("identifyService")
public class IdentifyServiceImpl extends CommonServiceImpl<Identify,String> implements IdentifyService{

	private IdentifyDao identifyDao;

	@Autowired
	public IdentifyServiceImpl(IdentifyDao identifyDao) {
		super(identifyDao);
		this.identifyDao=identifyDao;
	}

}




