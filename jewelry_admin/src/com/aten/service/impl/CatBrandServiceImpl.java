package com.aten.service.impl;

import com.aten.model.orm.CatBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.CatBrandDao;
import com.aten.service.CatBrandService;
import org.springframework.transaction.annotation.Transactional;

@Service("catBrandService")
@Transactional
public class CatBrandServiceImpl extends CommonServiceImpl<CatBrand,String> implements CatBrandService{

	private CatBrandDao catBrandDao;

	@Autowired
	public CatBrandServiceImpl(CatBrandDao catBrandDao) {
		super(catBrandDao);
		this.catBrandDao=catBrandDao;
	}

	@Override
	public void deleteCatBrand(String cat_id) {
		catBrandDao.deleteCatBrand(cat_id);
	}
}




