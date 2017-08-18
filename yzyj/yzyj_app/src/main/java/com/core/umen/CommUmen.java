package com.core.umen;

import com.core.util.AppDesUtil;

/**
 * Created by wjf on 2016/11/4.
 */
public class CommUmen {
    public static Integer sendAndroidUnicast(String umengToken,java.util.HashMap map){
        Integer state = 0;
        try{
            //推送至POS消息
            //安卓
            UmenUtil umenUtil=new UmenUtil("5816afa1aed17909f900058c","ylpwvxjjv8entvzeozxftdpxlomo3xn3");
            String content = AppDesUtil.posAppDesEncrypt(map,"成功",1);
            umenUtil.sendAndroidUnicast(umengToken,"消息推送",content);
        }catch (Exception e ){
            e.printStackTrace();
        }
        return state;
    }
}
