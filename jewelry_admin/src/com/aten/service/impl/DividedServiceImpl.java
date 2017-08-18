package com.aten.service.impl;

import com.aten.model.orm.Divided;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.DividedDao;
import com.aten.service.DividedService;

import java.util.HashMap;
import java.util.List;

@Service("dividedService")
public class DividedServiceImpl extends CommonServiceImpl<Divided,String> implements DividedService{

	private DividedDao dividedDao;

	@Autowired
	public DividedServiceImpl(DividedDao dividedDao) {
		super(dividedDao);
		this.dividedDao=dividedDao;
	}

	@Override
	public List<Divided> findByAccountId(HashMap<String, Object> paraMap) {
		return dividedDao.findByAccountId(paraMap);
	}

	@Override
	public int getDetailCount(HashMap<String, Object> paraMap) {
		return dividedDao.getDetailCount(paraMap);
	}
}




