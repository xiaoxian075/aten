package com.aten.service.impl;

import com.aten.model.orm.CatAttr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aten.dao.CatAttrDao;
import com.aten.service.CatAttrService;

import java.util.List;

@Service("catAttrService")
@Transactional
public class CatAttrServiceImpl extends CommonServiceImpl<CatAttr,String> implements CatAttrService{

	private CatAttrDao catAttrDao;

	@Autowired
	public CatAttrServiceImpl(CatAttrDao catAttrDao) {
		super(catAttrDao);
		this.catAttrDao=catAttrDao;
	}

	@Override
	public void deleteCatSkuAttr(String cat_id) {
		catAttrDao.deleteCatSkuAttr(cat_id);
	}

	@Override
	public void deleteCatKeyAttr(String cat_id) {
		catAttrDao.deleteCatKeyAttr(cat_id);
	}

	@Override
	public List<CatAttr> getByCatId(String cat_id) {
		return catAttrDao.getByCatId(cat_id);
	}

	@Override
	public List<CatAttr> findByAttrId(String attr_id) {
		return catAttrDao.findByAttrId(attr_id);
	}
}




