package com.aten.service.impl;

import com.aten.model.orm.GoodsCatCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.GoodsCatCountDao;
import com.aten.service.GoodsCatCountService;

@Service("goodsCatCountService")
public class GoodsCatCountServiceImpl extends CommonServiceImpl<GoodsCatCount,String> implements GoodsCatCountService{

	private GoodsCatCountDao goodsCatCountDao;

	@Autowired
	public GoodsCatCountServiceImpl(GoodsCatCountDao goodsCatCountDao) {
		super(goodsCatCountDao);
		this.goodsCatCountDao=goodsCatCountDao;
	}

}




