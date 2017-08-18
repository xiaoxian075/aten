package com.aten.service.impl;

import com.aten.model.orm.Power;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.PowerDao;
import com.aten.service.PowerService;

@Service("powerService")
public class PowerServiceImpl extends CommonServiceImpl<Power,String> implements PowerService{

	@Autowired
	public PowerServiceImpl(PowerDao powerDao) {
		super(powerDao);
	}

}




