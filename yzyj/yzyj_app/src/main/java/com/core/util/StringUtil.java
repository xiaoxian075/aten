package com.core.util;

/**
 * Created by Administrator on 2016/12/17.
 */

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class StringUtil {
    public static boolean isNotNull(String str) {
        boolean flag = false;
        if ((str != null) && (!str.isEmpty()) && (!"".equals(str.trim()))) {
            flag = true;
        }
        return flag;
    }

    public static boolean isNull(String str) {
        boolean flag = false;
        if ((str == null) || (str.isEmpty()) || ("".equals(str.trim()))) {
            flag = true;
        }
        return flag;
    }

    public static String getRandomCode() {
        String code = "";
        for (int i = 0; i < 6; i++) {
            int c = (int) (Math.random() * 10.0D);
            code = code + c;
        }
        return code;
    }

    public static String dealNull(String str) {
        if (str != null) {
            return str;
        }
        return "";
    }

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (date == null) {
            return sdf.format(new Date());
        }
        return sdf.format(date);
    }

    public static String fromDateH() {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format1.format(new Date());
    }

    public static String fromDateY(Date date) {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        return format1.format(date);
    }

    /**
     * 删除图片
     */
    public static void delFile(String path){
        File file = new File(path);
        if(file != null){
            if(file.exists()){
                file.delete();
            }
        }
    }

}