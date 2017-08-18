package com.aten.service;

import com.aten.model.orm.AttrValue;

import java.util.List;

public  interface AttrValueService extends CommonService<AttrValue, String>{

    List<AttrValue> findByAttrId(String attr_id);
}

