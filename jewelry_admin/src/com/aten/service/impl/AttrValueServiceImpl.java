package com.aten.service.impl;

import com.aten.model.orm.AttrValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aten.dao.AttrValueDao;
import com.aten.service.AttrValueService;

import java.util.List;

@Service("attrValueService")
public class AttrValueServiceImpl extends CommonServiceImpl<AttrValue,String> implements AttrValueService{

	private AttrValueDao attrValueDao;

	@Autowired
	public AttrValueServiceImpl(AttrValueDao attrValueDao) {
		super(attrValueDao);
		this.attrValueDao=attrValueDao;
	}

	@Override
	public List<AttrValue> findByAttrId(String attr_id) {
		return attrValueDao.findByAttrId(attr_id);
	}
}




