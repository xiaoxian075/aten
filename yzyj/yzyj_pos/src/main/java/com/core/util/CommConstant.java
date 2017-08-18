package com.core.util;

import com.admin.model.DevicePayCardQuota;
import com.admin.model.DictTable;

import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class CommConstant {
    public static String appPostDesKey = "DES2016%010#9";
    public static String yunRecYunDataDesKey = "hcr2016%$1008";
    public static String yunRechargeDesKey = "hcr2016@Asa1008";
    public static String yunGetCartDesKey="12345678";

    /**
     * 正式服务器地址
     */
    public static String yunRechargeUrl = "http://api.ipaye.cn/api/v2/payorder/hardwareCloudRecharge";//云支付 , 充值入口
    public static String yunGetCartUrl = "http://api.ipaye.cn/api/v2/bindbank/list";//云支付获取银行卡信息
    public static String yunGetUserDetail = "http://api.ipaye.cn/api/v2/user/getDetail";//云支付获取帐户信息

//    public static String yunRechargeUrl = "http://test-api.ipaye.cn/api/v2/payorder/hardwareCloudRecharge";//云支付 , 充值入口
//    public static String yunGetCartUrl = "http://test-api.ipaye.cn/api/v2/bindbank/list";//云支付获取银行卡信息
//    public static String yunGetUserDetail = "http://test-api.ipaye.cn/api/v2/user/getDetail";//云支付获取帐户信息
//    public static String cancelYunMerchantIdentifyUrl = "http://192.168.1.31:7070/yunpay_service/api/v2/user/cancelFlagPos";//取消pos机商户标识


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
     * 用户刷卡金额限制
     */
    public static java.util.HashMap <String,DevicePayCardQuota> devicePayCardQuotaMapList = new java.util.HashMap();
}
