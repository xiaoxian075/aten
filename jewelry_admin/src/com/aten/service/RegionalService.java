/*
 * 系统名称:艾腾云返酒店平台
 * (C)  Copyright aten  2017-04-28 17:41:43  Corporation 
 * All rights reserved.
 * Package:com.aten.service
 * FileName: RegionalService.java 
 * Author:linjunqin
 */
package com.aten.service;

import com.aten.model.orm.Regional;

import java.util.HashMap;
import java.util.List;

/**
 * @author linjunqin
 * @Function 区域交易管理  service接口
 * @date 2017-04-28 17:41:43
 */
public  interface RegionalService extends CommonService<Regional, String>{


    int getDetailCount(HashMap<String, Object> paraMap);

    List<Regional> getDetailList(HashMap<String, Object> paraMap);
    
    /**
	 * @author linjunqin
	 * @Description 验证交易统计是否已生成
	 * @param
	 * @date 2017-5-29 上午2:49:08
	 */
    public int getIsAreaCount(HashMap<String, String> paraMap);
    
}

