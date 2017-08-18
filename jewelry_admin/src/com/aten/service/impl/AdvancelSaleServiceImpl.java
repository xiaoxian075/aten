package com.aten.service.impl;

import com.aten.model.orm.AdvancelSale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.AdvancelSaleDao;
import com.aten.service.AdvancelSaleService;

@Service("advancelSaleService")
public class AdvancelSaleServiceImpl extends CommonServiceImpl<AdvancelSale,String> implements AdvancelSaleService{

	private AdvancelSaleDao advancelSaleDao;

	@Autowired
	public AdvancelSaleServiceImpl(AdvancelSaleDao advancelSaleDao) {
		super(advancelSaleDao);
		this.advancelSaleDao=advancelSaleDao;
	}

	@Override
	public AdvancelSale getByGoodsid(String goods_id) {
		return advancelSaleDao.getByGoodsid(goods_id);
	}

}




