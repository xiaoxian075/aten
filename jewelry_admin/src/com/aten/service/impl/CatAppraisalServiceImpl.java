package com.aten.service.impl;

import com.aten.model.orm.CatAppraisal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.CatAppraisalDao;
import com.aten.service.CatAppraisalService;
import org.springframework.transaction.annotation.Transactional;

@Service("catAppraisalService")
@Transactional
public class CatAppraisalServiceImpl extends CommonServiceImpl<CatAppraisal,String> implements CatAppraisalService{

	private CatAppraisalDao catAppraisalDao;

	@Autowired
	public CatAppraisalServiceImpl(CatAppraisalDao catAppraisalDao) {
		super(catAppraisalDao);
		this.catAppraisalDao=catAppraisalDao;
	}

	@Override
	public void deleteCatAppraisal(String cat_id) {
		catAppraisalDao.deleteCatAppraisal(cat_id);
	}
}




