package com.aten.service;

import com.aten.model.orm.Divided;

import java.util.HashMap;
import java.util.List;

public  interface DividedService extends CommonService<Divided, String>{


    List<Divided> findByAccountId(HashMap<String, Object> paraMap);

    int getDetailCount(HashMap<String, Object> paraMap);
}

