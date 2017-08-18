package com.aten.service.impl;

import com.aten.model.orm.Sysmsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.SysmsgDao;
import com.aten.service.SysmsgService;
import org.springframework.transaction.annotation.Transactional;

@Service("sysmsgService")
@Transactional
public class SysmsgServiceImpl extends CommonServiceImpl<Sysmsg,String> implements SysmsgService{

	private SysmsgDao sysmsgDao;

	@Autowired
	public SysmsgServiceImpl(SysmsgDao sysmsgDao) {
		super(sysmsgDao);
		this.sysmsgDao=sysmsgDao;
	}

}




