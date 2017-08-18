package com.aten.service.impl;

import com.aten.model.orm.CatSupply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.CatSupplyDao;
import com.aten.service.CatSupplyService;
import org.springframework.transaction.annotation.Transactional;

@Service("catSupplyService")
@Transactional
public class CatSupplyServiceImpl extends CommonServiceImpl<CatSupply,String> implements CatSupplyService{

	private CatSupplyDao catSupplyDao;

	@Autowired
	public CatSupplyServiceImpl(CatSupplyDao catSupplyDao) {
		super(catSupplyDao);
		this.catSupplyDao=catSupplyDao;
	}

	@Override
	public void deleteCatSupply(String cat_id) {
		catSupplyDao.deleteCatSupply(cat_id);

	}
}




