package com.aten.service.impl;

import com.aten.model.orm.Brand;
import com.communal.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.BrandDao;
import com.aten.service.BrandService;

import java.util.List;

@Service("brandService")
public class BrandServiceImpl extends CommonServiceImpl<Brand,String> implements BrandService{

	private BrandDao brandDao;

	@Autowired
	public BrandServiceImpl(BrandDao brandDao) {
		super(brandDao);
		this.brandDao=brandDao;
	}

	@Override
	public List<Brand> queryList(Query query) {
		return brandDao.queryList(query);
	}
	
	public List<Brand>selectByCatId(String catId){
		return brandDao.selectByCatId(catId);
	}
}




