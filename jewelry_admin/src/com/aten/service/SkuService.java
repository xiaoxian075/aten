package com.aten.service;

import java.math.BigInteger;
import java.util.List;

import com.aten.model.orm.SkuNode;

public interface SkuService extends CommonService<SkuNode, BigInteger>{

	List<SkuNode> getByGoodsid(String goods_id);

}
