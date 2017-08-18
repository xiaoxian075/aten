package com.aten.service.impl;

import com.aten.model.orm.FullSales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.FullSalesDao;
import com.aten.service.FullSalesService;

@Service("fullSalesService")
public class FullSalesServiceImpl extends CommonServiceImpl<FullSales,String> implements FullSalesService{

	private FullSalesDao fullSalesDao;

	@Autowired
	public FullSalesServiceImpl(FullSalesDao fullSalesDao) {
		super(fullSalesDao);
		this.fullSalesDao=fullSalesDao;
	}

	@Override
	public FullSales getByGoodsid(String goods_id) {
		return fullSalesDao.getByGoodsid(goods_id);
	}

}




