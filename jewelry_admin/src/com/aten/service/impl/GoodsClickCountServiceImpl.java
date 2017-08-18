package com.aten.service.impl;

import com.aten.model.orm.GoodsClickCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.GoodsClickCountDao;
import com.aten.service.GoodsClickCountService;

@Service("goodsClickCountService")
public class GoodsClickCountServiceImpl extends CommonServiceImpl<GoodsClickCount,String> implements GoodsClickCountService{

	private GoodsClickCountDao goodsClickCountDao;

	@Autowired
	public GoodsClickCountServiceImpl(GoodsClickCountDao goodsClickCountDao) {
		super(goodsClickCountDao);
		this.goodsClickCountDao=goodsClickCountDao;
	}

}




