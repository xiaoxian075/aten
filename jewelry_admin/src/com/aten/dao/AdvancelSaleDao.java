package com.aten.dao;
import org.apache.ibatis.annotations.Param;

import com.aten.model.orm.AdvancelSale;

public interface AdvancelSaleDao extends CommonDao<AdvancelSale, String>{

	AdvancelSale getByGoodsid(String goods_id);
	
	int updateInfoByGoodsId(AdvancelSale advancelSale);

	void deleteByGoodsId(@Param("goods_id") String goods_id);
	
}


