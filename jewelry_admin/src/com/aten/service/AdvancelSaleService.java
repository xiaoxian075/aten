package com.aten.service;

import com.aten.model.orm.AdvancelSale;

public  interface AdvancelSaleService extends CommonService<AdvancelSale, String>{

	AdvancelSale getByGoodsid(String goods_id);
	

	
}

