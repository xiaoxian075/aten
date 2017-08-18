package com.aten.service;

import com.aten.model.orm.BaseExample;
import com.aten.model.orm.CustomizedPage;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  interface CustomizedPageService extends CommonService<CustomizedPage, String>{
    java.util.HashMap selectCustomizedPageMap(BaseExample baseExample);
    List<HashMap> selectActivityGoodsList(Map map);
    Integer selectActivityGoodsCount(Map map);
    Integer insertPageModule(CustomizedPage customizedPage,String jsonData);
    Integer insertView(CustomizedPage customizedPage);
    Integer updateCustomizedPage(CustomizedPage customizedPage);
    Integer deletePage(HttpServletRequest request, CustomizedPage customizedPage);

	
}

