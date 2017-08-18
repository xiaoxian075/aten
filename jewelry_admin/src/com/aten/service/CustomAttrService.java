package com.aten.service;

import java.math.BigInteger;
import java.util.List;

import com.aten.model.dto.AttrDto;
import com.aten.model.orm.CustomAttr;
import com.aten.model.orm.CustomAttrNode;

public  interface CustomAttrService extends CommonService<CustomAttr, String>{
	
	public List<AttrDto> getCustomAttrList(String quote_id,String attr_type);

	public List<CustomAttrNode> getByGoodsid(BigInteger goods_id);
	
}

