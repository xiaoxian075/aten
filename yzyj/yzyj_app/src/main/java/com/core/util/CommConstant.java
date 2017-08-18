package com.core.util;

import com.admin.model.DictTable;

import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class CommConstant {
    public static boolean blnOrderJob = false;
    public static String desDBKey = "12345678";
    public static String appPostDesKey = "YZYJ#LFJSF#93%FE@3";
    public static String yunRecYunDataDesKey = "hcr2016%$1008";
    public static String yunSignDesKey="hcr2016@Asa1008";
    public static String yunGetCartDesKey="12345678";
//    public static String yunGetUserDetail = "http://api.ipaye.cn/api/v2/user/getDetail";//云支付帐户信息

    /**
     * 调用云支付 提现 接口 - 使用内网地址接口
     */
//    public static String yunHanderPay = "http://api.ipaye.cn/api/v2/payorder/handerPay"; //云支付 提现接口
//    public static String yunGetUserDetail = "http://api.ipaye.cn/api/v2/user/getDetail";//云支付获取帐户信息

    public static String yunHanderPay = "http://192.168.1.31:7070/yunpay_service/api/v2/payorder/handerPay"; //云支付 提现接口
    public static String yunGetUserDetail = "http://192.168.1.31:7070/yunpay_service/api/v2/user/getDetail";//云支付获取帐户信息

    public static String baseUrl = "http://admin.gdyzyj.com/yzpos/picCommon/infoPic/";//pos机后台
    public static String headUrl = "http://app.gdyzyj.com/yzapp/head/";//app应用
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

}
