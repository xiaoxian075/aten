package com.aten.dao;
import com.aten.model.orm.GoodsCustomAttrValue;

public interface GoodsCustomAttrValueDao extends CommonDao<GoodsCustomAttrValue, String>{
	
	int deleteByGoodsId(String goods_id);
}


