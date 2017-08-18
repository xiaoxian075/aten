package com.aten.service.impl;

import com.aten.model.orm.ShakeAwards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.ShakeAwardsDao;
import com.aten.service.ShakeAwardsService;

@Service("shakeAwardsService")
public class ShakeAwardsServiceImpl extends CommonServiceImpl<ShakeAwards,String> implements ShakeAwardsService{

	private ShakeAwardsDao shakeAwardsDao;

	@Autowired
	public ShakeAwardsServiceImpl(ShakeAwardsDao shakeAwardsDao) {
		super(shakeAwardsDao);
		this.shakeAwardsDao=shakeAwardsDao;
	}

}




