package com.aten.service.impl;

import com.aten.model.orm.ManaFunds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.ManaBillsDao;
import com.aten.dao.ManaFundsDao;
import com.aten.service.ManaFundsService;

@Service("manaFundsService")
public class ManaFundsServiceImpl extends CommonServiceImpl<ManaFunds,String> implements ManaFundsService{

	private ManaFundsDao manaFundsDao;

	@Autowired
	public ManaFundsServiceImpl(ManaFundsDao manaFundsDao) {
		super(manaFundsDao);
		this.manaFundsDao=manaFundsDao;
	}

	
	
	
}




