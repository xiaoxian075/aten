package com.aten.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.aten.model.orm.CustomAttrValueNode;

public interface CustomAttrValueDao extends CommonDao<CustomAttrValueNode, BigInteger>{

	List<CustomAttrValueNode> getByCustomattridAndAttrid(Map<String, Object> param);

}
