package com.aten.dao;
import com.aten.model.orm.BaseExample;
import com.aten.model.orm.CustomizedPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CustomizedPageDao extends CommonDao<CustomizedPage, String>{
    List<CustomizedPage> selectByExample(BaseExample baseExample);
    List<CustomizedPage> selectCustomizedPageMap(BaseExample baseExample);
    List<HashMap> selectActivityGoodsList(Map map);
    Integer selectActivityGoodsCount(Map map);
    Integer insertCustomizedPage(CustomizedPage customizedPage);
    Integer insertModule(CustomizedPage customizedPage);
    Integer insertView(CustomizedPage customizedPage);
    Integer updateCustomizedPage(CustomizedPage customizedPage);
    Integer updateModule(CustomizedPage customizedPage);
    Integer deleteModule(CustomizedPage customizedPage);
    Integer deleteView(CustomizedPage customizedPage);
}


