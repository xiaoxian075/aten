package com.core.util;

import com.admin.model.AgencyPerson;
import com.admin.model.User;
import com.admin.vo.CommAppVo;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2016/9/7.
 */
public class AppFunUtil {

    /**
     * 1.解密 , 转码
     * @param data
     * @return
     */
    public static java.util.HashMap getDataMap(String data){
        try{
            CommAppVo commAppVo = AppDesUtil.posAppDesDecrypt(data);
            if("jsonErr".equals(commAppVo.getMessage())){
                Log.exception("AppDesUtil.getData.jsonErr");
            }
            if(commAppVo.getSuccess()){
                return commAppVo.getHashMap();
            }else{
                Log.exception("AppDesUtil.getData."+commAppVo.getMessage());
            }
        }catch (Exception e ){

        }
        return null;
    }
    public static User getUser(HttpServletRequest request) {
        User userInfo = (User) request.getSession().getAttribute("userInfo");
        if(userInfo != null){
            return userInfo;
        }else{
            userInfo = new User();
        }
        return userInfo;
    }

    public static AgencyPerson getAgencyPerson(HttpServletRequest request) {
        AgencyPerson agencyPersonInfo = (AgencyPerson) request.getSession().getAttribute("agencyPerson");
        if(agencyPersonInfo != null){
            return agencyPersonInfo;
        }else{
            agencyPersonInfo = new AgencyPerson();
        }
        return agencyPersonInfo;
    }


    /**
     * 获取访问者IP
     *
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     *
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request){
        try{
            String ip = request.getHeader("X-Real-IP");
            if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
            ip = request.getHeader("X-Forwarded-For");
            if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
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

    /**
     * 应用于 pos机接口加密
     */
    public static void getReturnOutStr(HttpServletResponse response, String retStr, String charsetName) {
        response.setContentType("text/html; charset="+charsetName);
        PrintWriter out = null;
        try {
            //设置响应属性
            out = response.getWriter();
            out.write(retStr);
            out.flush();
            out.close();
        }
        catch (Exception ex) {
            try {
                out.flush();
                out.close();
            }
            catch (Exception et) {}
        }
        finally {
            try {
                out.flush();
                out.close();
            }
            catch (Exception ex) {}

        }
    }

    /**
     * 获取 Map 数据
     * @param request
     * @return
     */
    public static java.util.HashMap getClientPostMap(HttpServletRequest request,String appPostDesKey) {
        java.util.HashMap map = new java.util.HashMap();
        try {
            String req = getClientPostStr(request,"UTF-8");
            //解密
            req = DesUtil.decrypt(req, appPostDesKey);
            map = AppDesUtil.toLinkedHashMap(req);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
        }
        return map;
    }

    public static String getClientPostStr(HttpServletRequest request,String charsetName) {
        String result = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    request.getInputStream(), charsetName));
            char tagChar[];
            tagChar = new char[1024];
            int len;
            String temp = "";
            while ( (len = reader.read(tagChar)) != -1) {
                temp = new String(tagChar, 0, len);
                result += temp;
                temp = null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
        }
        return result;
    }

    /**
     * 返回加密数据
     */
    public static Integer returnDataClient(HttpServletResponse response,Object obj,String message,Integer statusCode,String appPostDesKey) {
        Integer state = 0;
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("{");
            stringBuffer.append("message:").append(message);
            stringBuffer.append("statusCode:").append(statusCode);
            stringBuffer.append("}");
            String data = AppDesUtil.getDesEncrypt(obj,message,statusCode,appPostDesKey);
            getReturnOutStr(response,data,"UTF-8");
            state = 1;
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return state;
    }
}
