package com.aten.dao;
import org.apache.ibatis.annotations.Param;

import com.aten.model.orm.FullSales;

public interface FullSalesDao extends CommonDao<FullSales, String>{

	FullSales getByGoodsid(String goods_id);
	
	int updateInfoByGoodsId(FullSales fullSales);

	void deleteByGoodsId(@Param("goods_id") String goods_id);
	
}


