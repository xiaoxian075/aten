package com.aten.service.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aten.dao.SkuDao;
import com.aten.model.orm.SkuNode;
import com.aten.service.SkuService;

@Service("skuService")
public class SkuServiceImpl extends CommonServiceImpl<SkuNode,BigInteger> implements SkuService{
	
	private SkuDao skuDao;

	@Autowired
	public SkuServiceImpl(SkuDao skuDao) {
		super(skuDao);
		this.skuDao = skuDao;
	}

	@Override
	public List<SkuNode> getByGoodsid(String goods_id) {
		return skuDao.getByGoodsid(goods_id);
	}

}
