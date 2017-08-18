package com.aten.service.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aten.dao.CustomAttrValueDao;
import com.aten.model.orm.CustomAttrValueNode;
import com.aten.service.CustomAttrValueService;

@Service("customAttrValueService")
public class CustomAttrValueServiceImpl extends CommonServiceImpl<CustomAttrValueNode, BigInteger> implements CustomAttrValueService{

	private CustomAttrValueDao dao;
	
	@Autowired
	public CustomAttrValueServiceImpl(CustomAttrValueDao dao) {
		super(dao);
		this.dao = dao;
	}

	@Override
	public List<CustomAttrValueNode> getByCustomattridAndAttrid(BigInteger custom_attr_id, BigInteger attr_id) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("custom_attr_id", custom_attr_id);
		param.put("attr_id", attr_id);
		return dao.getByCustomattridAndAttrid(param);
	}

}
