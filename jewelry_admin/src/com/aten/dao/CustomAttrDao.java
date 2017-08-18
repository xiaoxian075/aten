package com.aten.dao;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import com.aten.model.dto.AttrDto;
import com.aten.model.dto.AttrValueDto;
import com.aten.model.orm.CustomAttr;
import com.aten.model.orm.CustomAttrNode;

public interface CustomAttrDao extends CommonDao<CustomAttr, String>{
	public void deleteAttrByMap(HashMap<String, String> paraMap);
	
	public List<AttrDto> getAttrList(HashMap<String, String> paraMap);
	
	public List<AttrValueDto> getAttrValueList(HashMap<String, String> paraMap);

	public List<CustomAttrNode> getByGoodsid(BigInteger goods_id);
}


