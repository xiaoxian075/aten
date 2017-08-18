package com.core.util;

import com.admin.model.UserHandle;
import com.admin.vo.UserHandleVo;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 * Created by WuJinFei on 2016/8/30.
 */
public class CommUserHandle {
    public static String UserHandleCompare(Object oldVo , Object newVo){
        String retStr = null ;
        try{
            //获取newVo中的所有方法 get/set
            Method[] arrMethod= newVo.getClass().getDeclaredMethods();
            //循环 遍历
            for(Method method:arrMethod ){
                //获取方法名称
                String mName = method.getName();
                if(mName.indexOf("get") != -1){
                    //获取方法返回类型
                    String type = method.getReturnType().getName();
                    if("java.util.Date".equals(type)){
                        java.util.Date value =(java.util.Date) method.invoke(oldVo);
                        java.util.Date value2 = (java.util.Date) method.invoke(newVo);
                        if(dateLong(value) != dateLong(value2) ){
                            retStr = bodyFormat(retStr,mName, FunUtil.getFormatDate(value,""), FunUtil.getFormatDate(value2,""));
                        }
                    }else if("java.lang.String".equals(type)) {
                        String value = objToString(method.invoke(oldVo));
                        String value2 = objToString(method.invoke(newVo));
                        if(!value.equals(value2)){
                            retStr = bodyFormat(retStr,mName,value,value2);
                        }
                    }else{
                        Object value = method.invoke(oldVo);
                        Object value2 = method.invoke(newVo);
                        if(hashCode(value) != hashCode(value2)){
                            retStr = bodyFormat(retStr,mName,value,value2);
                        }
                    }

                }
            }

        }catch (Exception e ){
            e.printStackTrace();
        }
        return retStr;
    }
    public static String bodyFormat(String retStr, String mName, String value, String value2){
        try{
            if(retStr == null){
                retStr = "";
            }
            retStr = retStr + mName+":值"+value+"变更" +value2+",";
        }catch (Exception e ){

        }
        return retStr;
    }
    public static String bodyFormat(String retStr, String mName, Object value, Object value2){
        try{
            if(retStr == null){
                retStr = "";
            }
            retStr = retStr + mName+":值"+value+"变更" +value2+",";
        }catch (Exception e ){

        }
        return retStr;
    }

    public static int hashCode(Object obj){
        try{
            return obj.hashCode();
        }catch (Exception e ){

        }
        return -1;
    }
    public static Long dateLong(Date date){
        try{
            return date.getTime();
        }catch (Exception e ){

        }
        return null;
    }
    public static String objToString(Object obj){
        try{
            return obj.toString();
        }catch (Exception e ){

        }
        return "";
    }
    /**
     * 初始用户权限
     */
    public static void InitUserHandleData(List<UserHandle> userHandleList, HttpServletRequest request){
        try{
            java.util.HashMap<String,UserHandleVo> map = new java.util.HashMap();
            for (UserHandle userHandle:userHandleList){
                UserHandleVo vo = new UserHandleVo();
                vo.setUserHandle(userHandle.getHandleArray());
                dealHandleArray(vo);
                map.put(userHandle.getModuleId(),vo);
            }
            request.getSession().setAttribute("user_handle", map);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    /**
     * 获取用户操作权限
     */
    public static UserHandleVo getUserHandle(String moduleId, HttpServletRequest request){
        UserHandleVo userHandleVo = null;
        try{
            javax.servlet.http.HttpSession hs = request.getSession();
            java.util.HashMap<String,UserHandleVo> map  = (java.util.HashMap) hs.getAttribute("user_handle");
            userHandleVo = map.get(moduleId);
        }catch (Exception e ){
            e.printStackTrace();
        }
        return userHandleVo;
    }

    /**
     * 验证用户权限
     */
    public static Integer checkUserHandle(String moduleId, String handle, HttpServletRequest request){
        Integer state = 0;
        try{
            UserHandleVo userHandleVo = getUserHandle(moduleId,request);
            // 检查用户权限是否存在
            if(userHandleVo.getUserHandle().indexOf(handle+",") != -1){
                //权限存在
                state = 1;
            }else{
                //权限不存在
                state = 0;
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return state;
    }

    private static Integer dealHandleArray(UserHandleVo userHandleVo){
        Integer state = 0;
        try{
            String[] arrStr = userHandleVo.getUserHandle().split(",");
            for (String key : arrStr){
                switch (key){
                    case "1":
                        userHandleVo.setAdd(key);
                        break;
                    case "2":
                        userHandleVo.setEdit(key);
                        break;
                    case "3":
                        userHandleVo.setDel(key);
                        break;
                }
            }
            state = 1;
        }catch (Exception e ){
            e.printStackTrace();
        }
        return state;
    }

}
