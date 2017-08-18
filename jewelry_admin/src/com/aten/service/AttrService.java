package com.aten.service;

import java.util.List;
import java.util.Map;

import com.aten.model.orm.Attr;
import com.aten.model.orm.Cat;
import com.communal.util.Query;

public  interface AttrService extends CommonService<Attr, String>{


    List<Attr> queryList(Query query);
    
    Map getAttrByCatId(Cat cat,String goods_id);

    void limitState(String parameter_id);

    void enableState(String parameter_id);

    void batchdelete(String[] ids);
}

