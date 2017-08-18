package com.core.util;

/**
 * Created by Administrator on 2016/10/10.
 */
public class Log {
    public static void exception(String str){
        try{
            if("1".equals(CommConstant.logOutState)) {
                System.out.println(str);
            }
        }catch (Exception e ){

        }
    }
    public static void out(String key,String str){
        try{
            if("1".equals(CommConstant.logOutState)) {
                System.out.println(key+":"+str);
            }
        }catch (Exception e ){

        }
    }
}
