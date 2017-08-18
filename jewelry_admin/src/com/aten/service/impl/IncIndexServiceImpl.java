package com.aten.service.impl;

import com.aten.model.orm.IncIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.IncIndexDao;
import com.aten.service.IncIndexService;

@Service("incIndexService")
public class IncIndexServiceImpl extends CommonServiceImpl<IncIndex,String> implements IncIndexService{

	private IncIndexDao incIndexDao;

	@Autowired
	public IncIndexServiceImpl(IncIndexDao incIndexDao) {
		super(incIndexDao);
		this.incIndexDao=incIndexDao;
	}

}




