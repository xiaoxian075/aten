package com.aten.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.aten.model.orm.GoodsCustomSku;

	
public interface GoodsCustomSkuDao extends CommonDao<GoodsCustomSku, String>{
	
	GoodsCustomSku getOneById(@Param("sku_id") String sku_id,
							  @Param("goods_id")String goods_id);
	
	int updateCustomSku(GoodsCustomSku goodsCustomSku);
	
	void batchDeleteNotIncludeOldSku(@Param("list") List<Object> list,
									 @Param("goods_id") String goods_id);
	
}


