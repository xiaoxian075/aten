package com.aten.dao;
import com.aten.model.orm.AttrValue;

import java.util.List;

public interface AttrValueDao extends CommonDao<AttrValue, String>{

    List<AttrValue> findByAttrId(String attr_id);
}


