package com.aten.service;

import com.aten.model.orm.Cat;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public  interface PreService extends CommonService<Cat, String>{


    void saveInfo(HttpServletRequest request, Cat cat);

    void updateInfo(HttpServletRequest request, Cat cat);
}

