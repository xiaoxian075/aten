package com.aten.service.impl;

import com.aten.model.orm.GoodsSaleCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.GoodsSaleCountDao;
import com.aten.service.GoodsSaleCountService;

@Service("goodsSaleCountService")
public class GoodsSaleCountServiceImpl extends CommonServiceImpl<GoodsSaleCount,String> implements GoodsSaleCountService{

	private GoodsSaleCountDao goodsSaleCountDao;

	@Autowired
	public GoodsSaleCountServiceImpl(GoodsSaleCountDao goodsSaleCountDao) {
		super(goodsSaleCountDao);
		this.goodsSaleCountDao=goodsSaleCountDao;
	}

}




