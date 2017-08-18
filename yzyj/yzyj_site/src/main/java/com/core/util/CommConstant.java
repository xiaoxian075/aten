package com.core.util;

import com.admin.model.DictTable;

import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class CommConstant {
    public static String desDBKey = "12345678";
    public static String appPostDesKey = "DES21210#9";
    public static String ADVERT_PATH = "/upload/advert/";
    public static String NAVIGATION_PATH = "/upload/navigation/";
    public static String SERVICE_PATH = "/upload/service/";
    public static String PLAN_PATH = "/upload/plan/";
    public static String PRODUCT_PATH = "/upload/product/";

    /**
     * 上传文件目录
     */
    public static String filePath = "";
    public static String testOrPoduction = "";//测试或生产环境
    public static String supplierNumber = "";
    public static String logOutState = "1";
    /**
     * 字典静态参数
     */
    public static java.util.HashMap <String,String> dictConstantMap = new  java.util.HashMap();
    /**
     * 字典Map列表
     */
    public static java.util.HashMap <String,List<DictTable>> dictMapList  = new java.util.HashMap();
    /**
     * 字典Map列表
     */
    public static String PROPAGANDA_PATH = "/upload/propaganda/";
}
