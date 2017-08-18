package com.aten.service.impl;

import com.aten.model.orm.GoodsCustomAttrValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.GoodsCustomAttrValueDao;
import com.aten.service.GoodsCustomAttrValueService;

@Service("goodsCustomAttrValueService")
public class GoodsCustomAttrValueServiceImpl extends CommonServiceImpl<GoodsCustomAttrValue,String> implements GoodsCustomAttrValueService{

	private GoodsCustomAttrValueDao goodsCustomAttrValueDao;

	@Autowired
	public GoodsCustomAttrValueServiceImpl(GoodsCustomAttrValueDao goodsCustomAttrValueDao) {
		super(goodsCustomAttrValueDao);
		this.goodsCustomAttrValueDao=goodsCustomAttrValueDao;
	}

}




