package com.aten.service.impl;

import com.aten.model.orm.PreGoodscat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.PreGoodscatDao;
import com.aten.service.PreGoodscatService;

@Service("preGoodscatService")
public class PreGoodscatServiceImpl extends CommonServiceImpl<PreGoodscat,String> implements PreGoodscatService{

	private PreGoodscatDao preGoodscatDao;

	@Autowired
	public PreGoodscatServiceImpl(PreGoodscatDao preGoodscatDao) {
		super(preGoodscatDao);
		this.preGoodscatDao=preGoodscatDao;
	}

	@Override
	public void deletePreCat(String precat_id) {
		preGoodscatDao.deletePreCat(precat_id);
	}
}




