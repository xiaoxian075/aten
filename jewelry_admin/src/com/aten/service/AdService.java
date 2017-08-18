/*
 * 系统名称:艾腾云返酒店平台
 * (C)  Copyright aten  2017-02-14 09:39:53  Corporation 
 * All rights reserved.
 * Package:com.aten.service
 * FileName: AdService.java 
 * Author:linjunqin
 */
package com.aten.service;

import com.aten.model.orm.Ad;

/**
 * @author linjunqin
 * @Function 广告类  service接口
 * @date 2017-02-14 09:39:53
 */
public  interface AdService extends CommonService<Ad, String>{
	

	/**
	 * @author linjunqin
	 * @Description 添加广告,并更新广告值
	 * @param
	 * @date 2017-2-16 下午7:25:48
	 */
	public void insertAffair(Ad ad);
	
	/**
	 * @author linjunqin
	 * @Description 删除广告并更新广告位的状态
	 * @param
	 * @date 2017-2-16 下午7:26:37
	 */
	public void deleteAffair(String id);
	
}

