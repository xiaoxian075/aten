package com.aten.dao;

import java.math.BigInteger;
import java.util.List;

import com.aten.model.orm.SkuNode;

public interface SkuDao extends CommonDao<SkuNode, BigInteger>{

	List<SkuNode> getByGoodsid(String goods_id);

}
