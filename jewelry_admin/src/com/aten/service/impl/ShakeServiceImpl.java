package com.aten.service.impl;

import com.aten.model.orm.Shake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.ShakeDao;
import com.aten.service.ShakeService;

@Service("shakeService")
public class ShakeServiceImpl extends CommonServiceImpl<Shake,String> implements ShakeService{

	private ShakeDao shakeDao;

	@Autowired
	public ShakeServiceImpl(ShakeDao shakeDao) {
		super(shakeDao);
		this.shakeDao=shakeDao;
	}

}




