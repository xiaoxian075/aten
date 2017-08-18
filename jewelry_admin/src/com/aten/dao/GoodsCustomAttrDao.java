package com.aten.dao;
import com.aten.model.orm.GoodsCustomAttr;

public interface GoodsCustomAttrDao extends CommonDao<GoodsCustomAttr, String>{
	
	int deleteByGoodsId(String goods_id);
	
}


