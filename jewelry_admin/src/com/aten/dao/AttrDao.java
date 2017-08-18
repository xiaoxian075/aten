package com.aten.dao;
import com.aten.model.orm.Attr;
import com.aten.model.orm.AttrVo;
import com.communal.util.Query;

import java.util.List;

public interface AttrDao extends CommonDao<Attr, String>{
    List<Attr> queryList(Query query);
    
    List<AttrVo>getAttrByCatId(List<String> list);
    
    List<AttrVo>getAttrBySku(List<String> list);
}


