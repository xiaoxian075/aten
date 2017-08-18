package com.aten.service.impl;

import com.aten.model.orm.Integral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.IntegralDao;
import com.aten.service.IntegralService;

@Service("integralService")
public class IntegralServiceImpl extends CommonServiceImpl<Integral,String> implements IntegralService{

	private IntegralDao integralDao;

	@Autowired
	public IntegralServiceImpl(IntegralDao integralDao) {
		super(integralDao);
		this.integralDao=integralDao;
	}

}




