package com.aten.service.impl;

import com.aten.model.orm.Payway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.PaywayDao;
import com.aten.service.PaywayService;

@Service("paywayService")
public class PaywayServiceImpl extends CommonServiceImpl<Payway,String> implements PaywayService{

	private PaywayDao paywayDao;

	@Autowired
	public PaywayServiceImpl(PaywayDao paywayDao) {
		super(paywayDao);
		this.paywayDao=paywayDao;
	}

}




