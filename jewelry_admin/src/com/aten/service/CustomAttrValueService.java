package com.aten.service;

import java.math.BigInteger;
import java.util.List;

import com.aten.model.orm.CustomAttrValueNode;

public interface CustomAttrValueService extends CommonService<CustomAttrValueNode, BigInteger>{

	public List<CustomAttrValueNode> getByCustomattridAndAttrid(BigInteger custom_attr_id, BigInteger attr_id);

}
