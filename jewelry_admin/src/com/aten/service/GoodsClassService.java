package com.aten.service;

import com.aten.model.orm.Cat;
import com.aten.model.orm.GoodsClass;

import javax.servlet.http.HttpServletRequest;

public interface GoodsClassService extends CommonService<GoodsClass, String>{

    void saveInfo(HttpServletRequest request,Cat cat);

    void updateInfo(HttpServletRequest request, Cat cat);
}
