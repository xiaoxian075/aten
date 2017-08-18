package com.aten.service.impl;

import com.aten.model.orm.Appraisal;
import com.communal.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.AppraisalDao;
import com.aten.service.AppraisalService;

import java.util.List;

@Service("appraisalService")
public class AppraisalServiceImpl extends CommonServiceImpl<Appraisal,String> implements AppraisalService{

	private AppraisalDao appraisalDao;

	@Autowired
	public AppraisalServiceImpl(AppraisalDao appraisalDao) {
		super(appraisalDao);
		this.appraisalDao=appraisalDao;
	}

	@Override
	public List<Appraisal> queryList(Query query) {
		return appraisalDao.queryList(query);
	}
	public List<Appraisal>selectByCatId(String catId){
		return appraisalDao.selectByCatId(catId);
	}

}




