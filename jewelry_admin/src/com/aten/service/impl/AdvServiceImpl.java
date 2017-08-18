package com.aten.service.impl;

import com.aten.model.orm.Adv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.AdvDao;
import com.aten.service.AdvService;

@Service("advService")
public class AdvServiceImpl extends CommonServiceImpl<Adv,String> implements AdvService{

	private AdvDao advDao;

	@Autowired
	public AdvServiceImpl(AdvDao advDao) {
		super(advDao);
		this.advDao=advDao;
	}

}




