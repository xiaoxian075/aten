package com.aten.service;

import com.aten.model.orm.FullSales;

public  interface FullSalesService extends CommonService<FullSales, String>{

	FullSales getByGoodsid(String goods_id);
	

	
}

