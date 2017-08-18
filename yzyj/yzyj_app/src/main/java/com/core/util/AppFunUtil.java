package com.core.util;

import com.admin.dao.SmsLogDaoMapper;
import com.admin.model.BaseExample;
import com.admin.model.DeviceInfo;
import com.admin.model.SmsLog;
import com.admin.model.User;
import com.admin.service.DeviceInfoService;
import com.admin.service.UserService;
import com.admin.vo.CommAppVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.core.entity.JSONResult;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.io.Resources;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.*;

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
    /**
     * 验证凭证
     * @param token
     * @return
     */
    public static User checkToken(HttpServletRequest request, UserService userService, String token){
        User user = new User();
        try{

            if(org.springframework.util.StringUtils.isEmpty(token)){
                user.setState(92);
                user.setNote("凭证必填");
                return user;
            }
            user = userService.selectToken(token);
            if(user == null){
                user = new User();
                user.setState(93);
                user.setNote("凭证无效");
                return user;
            }
            if(!token.equals(user.getToken())){
                user.setState(94);
                user.setNote("凭证无效");
                return user;
            }
            Date sysDate = new Date();
            long lastTime = FunUtil.dateAddSecond(user.getLastTime(),7200).getTime();
            long sysTime = sysDate.getTime();
            if(sysTime > lastTime){
                user.setState(95);
                user.setNote("凭证过期");
                return user;
            }
            /**
             * 验证来访问IP
             * 检查上次 IP 是否跟访问IP相同 , 跳IP需要重登
             */
            if(!AppFunUtil.getIpAddr(request).equals(user.getIp())){
                // 检查 IP 不同, token 失效 重新登入
                user.setState(96);
                user.setNote("失败");
            }
            user.setState(1);
            user.setNote("成功");
        }catch (Exception e ){
            e.printStackTrace();
        }
        return user;
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

    public static java.util.HashMap  getClientAllParameter(HttpServletRequest request){
        java.util.HashMap map = null;
        try{
            String value ;
            //获取所有名称，并根据名称获取值
            map = new java.util.HashMap();
            Enumeration e = request.getParameterNames();
            while(e.hasMoreElements()){
                String name = (String) e.nextElement();
                value = request.getParameter(name);
                map.put(name,value);
            }
        }catch (Exception e ){

        }
        return map;
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

    public static String getRandomCode() {
        String code = "";
        for (int i = 0; i < 6; i++) {
            int c = (int) (Math.random() * 10.0D);
            code = code + c;
        }
        return code;
    }

    /**
     * 发送短信
     * @param smsLogDaoMapper
     * @param mobile
     * @param msg
     * @param smsType
     * @param code
     * @return
     * @throws Exception
     */
    public static String sendSms(SmsLogDaoMapper smsLogDaoMapper, String mobile, String msg, int smsType, String code) throws Exception {
        String url = CommDictList.getDictVal("sms_config","smsuri");
        String account = CommDictList.getDictVal("sms_config","smsname");
        String pswd = CommDictList.getDictVal("sms_config","smspwd");
        int smsInterval = FunUtil.stringToInteger(CommDictList.getDictVal("sms_config","smsInterval"));
        int needStatusInt = FunUtil.stringToInteger(CommDictList.getDictVal("sms_config","needstatus"));
        boolean needstatus = false;
        if(needStatusInt == 1){
            needstatus = true;
        }
        Map m = new HashMap();
        m.put("mobile", mobile);
        m.put("smsInterval", Integer.valueOf(smsInterval));
        SmsLog sm = smsLogDaoMapper.querySmsLogByMap(m);
        if (sm == null) {
            String result = HttpSender.batchSend(url, account, pswd, mobile, msg, needstatus, null, null);
            String re = result.substring(15, 16);
            SmsLog smslog = new SmsLog();
            smslog.setMobile(mobile);
            smslog.setMsg(msg);
            smslog.setType(smsType);
            smslog.setTime(new Date());
            smslog.setCode(code);
            smslog.setIsSend(Integer.parseInt(re));
            smslog.setResult(result);
            smsLogDaoMapper.insert(smslog);
            return result;
        }
        return "200";
    }

    public static boolean checkSmscodeAvailable(HttpServletRequest request,String smsCode, String sessionAttrName, String mobile) {
        if ((request == null) || (StringUtil.isNull(smsCode)) || (StringUtil.isNull(sessionAttrName))) {
            return false;
        }
        String sessionAttrValue = (String) request.getSession().getAttribute(sessionAttrName);
        if (StringUtil.isNull(sessionAttrValue)) {
            return false;
        }
        String sessionMobile = (String) request.getSession().getAttribute("mobile");
        if (StringUtil.isNull(sessionMobile)) {
            return false;
        }
        if (!smsCode.equals(sessionAttrValue)) {
            return false;
        }
        if (!mobile.equals(sessionMobile)) {
            return false;
        }
        return true;
    }

}
