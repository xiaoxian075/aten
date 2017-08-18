package com.aten.service.impl;

import com.aten.model.orm.GoodsCustomAttr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.GoodsCustomAttrDao;
import com.aten.service.GoodsCustomAttrService;

@Service("goodsCustomAttrService")
public class GoodsCustomAttrServiceImpl extends CommonServiceImpl<GoodsCustomAttr,String> implements GoodsCustomAttrService{

	private GoodsCustomAttrDao goodsCustomAttrDao;

	@Autowired
	public GoodsCustomAttrServiceImpl(GoodsCustomAttrDao goodsCustomAttrDao) {
		super(goodsCustomAttrDao);
		this.goodsCustomAttrDao=goodsCustomAttrDao;
	}

}




