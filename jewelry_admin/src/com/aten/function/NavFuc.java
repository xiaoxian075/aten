package com.aten.function;

import com.aten.client.RedisClient;
import com.communal.constants.RedisConstants;




public class NavFuc {
	
	/**
	 * @author linjunqin
	 * @Description 根据请求地址返回导航串名称
	 * @param
	 * @date 2017-3-23 上午11:36:26
	 */
	public static String getNavName(String url){
		try {
			 String navStr = RedisClient.getStr(RedisConstants.NAVPOST+url);
			 if(navStr!=null && navStr.length()>0){
				 navStr = navStr.replace("> -", "").trim();
			 }
			 return navStr;
		} catch (Exception e) {
			e.printStackTrace();
			return new String();
		}
	}
	
}
