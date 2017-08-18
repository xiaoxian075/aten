package com.communal.util;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {
	
	/**
	 * @author linjunqin
	 * @Description 获取用户的IP
	 * @param
	 * @date 2017-2-16 下午12:01:21
	 */
    public static String getIpAddress(HttpServletRequest request){
        try{
            String ip = request.getHeader("X-Real-IP");
            if (!StringUtil.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
            ip = request.getHeader("X-Forwarded-For");
            if (!StringUtil.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
                // 多次反向代理后会有多个IP值，第一个为真实IP。
                int index = ip.indexOf(',');
                if (index != -1) {
                    return ip.substring(0, index);
                } else {
                    return ip;
                }
            } else {
                return request.getRemoteAddr();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
