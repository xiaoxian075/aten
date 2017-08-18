package com.aten.service.impl;

import com.aten.model.orm.CatRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.CatRateDao;
import com.aten.service.CatRateService;

import java.util.List;

@Service("catRateService")
public class CatRateServiceImpl extends CommonServiceImpl<CatRate,String> implements CatRateService{

	private CatRateDao catRateDao;

	@Autowired
	public CatRateServiceImpl(CatRateDao catRateDao) {
		super(catRateDao);
		this.catRateDao=catRateDao;
	}

	@Override
	public List<CatRate> getByCatId(String cat_id) {
		return catRateDao.getByCatId(cat_id);
	}

}




