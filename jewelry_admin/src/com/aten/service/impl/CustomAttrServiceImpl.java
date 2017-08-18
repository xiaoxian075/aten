package com.aten.service.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import com.aten.model.dto.AttrDto;
import com.aten.model.dto.AttrValueDto;
import com.aten.model.orm.CustomAttr;
import com.aten.model.orm.CustomAttrNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.CustomAttrDao;
import com.aten.service.CustomAttrService;

@Service("customAttrService")
public class CustomAttrServiceImpl extends CommonServiceImpl<CustomAttr,String> implements CustomAttrService{

	private CustomAttrDao customAttrDao;

	@Autowired
	public CustomAttrServiceImpl(CustomAttrDao customAttrDao) {
		super(customAttrDao);
		this.customAttrDao=customAttrDao;
	}
	
	public List<AttrDto> getCustomAttrList(String quote_id,String attr_type){
		//获取属性列表
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("quote_id", quote_id);
		paraMap.put("attr_type", attr_type);
		List<AttrDto> attrList = this.customAttrDao.getAttrList(paraMap);
		//获取属性对应下的属性值列表
		for(int i=0;i<attrList.size();i++){
			AttrDto ad = attrList.get(i);
			paraMap.clear();
			paraMap.put("attr_id", ad.getAttr_id());
			paraMap.put("quote_id", quote_id);
			paraMap.put("attr_type", attr_type);
			List<AttrValueDto> avdList = this.customAttrDao.getAttrValueList(paraMap);
			ad.setAttrValueDtoList(avdList);
		}
		return attrList;
	}

	@Override
	public List<CustomAttrNode> getByGoodsid(BigInteger goods_id) {
		return customAttrDao.getByGoodsid(goods_id);
	}

	
}




