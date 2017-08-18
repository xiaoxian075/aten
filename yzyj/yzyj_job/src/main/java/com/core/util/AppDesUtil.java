package com.core.util;

import com.admin.vo.CommAppVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.crypto.IllegalBlockSizeException;
import java.util.*;

/**
 * Created by wjf on 2016/10/9.
 */
public class AppDesUtil {
    /**
     * 获取 Map 数据
     * @return
     */
    public static HashMap decryptDataToMap(String data,String appPostDesKey) {
        HashMap map = new HashMap();
        try {
            //解密
            data = DesUtil.decrypt(data, appPostDesKey);
            map = AppDesUtil.toLinkedHashMap(data);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
        }
        return map;
    }

    /**
     * 应用于 pos机接口加密
     */
    public static String posAppDesEncrypt(Object obj,String message,Integer statusCode){
        String retStr = "";
        try{
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("{");
            stringBuffer.append("message:").append(message);
            stringBuffer.append("statusCode:").append(statusCode);
            stringBuffer.append("}");
            retStr = getDesEncrypt(obj,message,statusCode, CommConstant.appPostDesKey);
        }catch (Exception e ){
            e.printStackTrace();
        }
        return retStr;
    }

    /**
     * 应用于 pos机接口解密
     */
    public static <T> T posAppDesDecrypt(String data,Class<T> clazz){
        try{
            //解密
            String req = null;
            try{
                req = DesUtil.decrypt(data, CommConstant.appPostDesKey);
            }catch (IllegalBlockSizeException i){
                Log.exception("解密失败:"+data);
            }
            if(req != null) {
                return JSON.parseObject(req, clazz);
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 应用于 pos机接口解密
     */
    public static CommAppVo posAppDesDecrypt(String data){
        CommAppVo commAppVo = new CommAppVo();
        try{
            commAppVo = desDecrypt(data, CommConstant.appPostDesKey);
        }catch (Exception e ){
        }
        return commAppVo;
    }
    /**
     * 应用于 pos机接口解密
     */
    public static CommAppVo desDecrypt(String data, String desKey){
        CommAppVo commAppVo = new CommAppVo();
        try{
            //解密
            String req = DesUtil.decrypt(data, desKey);
            Log.out("AppDesUtil.desDecrypt",req);
            HashMap map = toLinkedHashMap(req);
            if("1".equals(map.get("mapCode"))){
                commAppVo.setMessage("success");
                commAppVo.setStatusCode(1);
                commAppVo.setSuccess(true);
            }else{
                commAppVo.setMessage("fail");
                commAppVo.setStatusCode(FunUtil.stringToInteger(map.get("mapCode")+""));
                commAppVo.setSuccess(false);
            }
            commAppVo.setHashMap(map);
        }catch (Exception e ){
            commAppVo.setMessage("fail");
            commAppVo.setStatusCode(999);
            commAppVo.setSuccess(false);
        }
        return commAppVo;
    }
    public static HashMap<String, Object> toLinkedHashMap(String object) {
        HashMap<String, Object> data = new LinkedHashMap<String, Object>();
        try{
            data.put("mapCode","1");
            // 将json字符串转换成jsonObject
            JSONObject jsonObject = null;
            try{
                jsonObject = JSON.parseObject(object);
            }catch (Exception e ){
                data.put("mapCode","jsonErr");
                data.put("mapState","902");
                return data;
            }
            //最外层解析
            for(Object key : jsonObject.keySet()){
                Object val = jsonObject.get(key);
                if(val instanceof JSONArray){
                    JSONArray jsonArray = (JSONArray)val;
                    List list = new ArrayList();
                    for (Object o : jsonArray){
                        Map<String, Object> subData = new HashMap<String, Object>();
                        JSONObject sJson = JSON.parseObject(o.toString());
                        Iterator it = sJson.keySet().iterator();
                        // 遍历jsonObject数据，添加到Map对象
                        while (it.hasNext()) {
                            String subKey = String.valueOf(it.next());
                            Object subVal = sJson.get(subKey);
                            subData.put(subKey, subVal);
                        }
                        list.add(subData);
                    }
                    data.put(key.toString(),list);
                }else{
                    data.put(key.toString(), val);
                }
            }
        }catch (Exception e ){
            data.put("mapCode","mapErr");
            data.put("mapState","903");
        }
        return data;
    }
    /**
     * des 加密
     * obj 封装的 data 对象
     * message 消息
     * statusCode 状态
     * key 密钥
     * @return
     */
    public static String getDesEncrypt(Object obj,String message,Integer statusCode,String key){
        String retStr = "";
        try{
            HashMap map = new HashMap();
            map.put("message",message);
            map.put("statusCode",statusCode);
            map.put("data",obj);
            retStr = JSONObject.toJSONString(map);
            Log.out("AppDesUtil.getDesEncrypt",retStr);
            retStr = DesUtil.encrypt(retStr,key);
        }catch (Exception e ){
            e.printStackTrace();
        }
        return retStr;
    }


}
