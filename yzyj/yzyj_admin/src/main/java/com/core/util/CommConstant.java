package com.core.util;

import com.admin.model.DevicePayCardQuota;
import com.admin.model.DictTable;

import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class CommConstant {
    public static boolean blnOrderJob = true;
    public static String desDBKey = "12345678";
    public static String appPostDesKey = "DES2016%010#9";
    public static String yunRecYunDataDesKey = "hcr2016%$1008";
    public static String yunRechargeDesKey = "hcr2016@Asa1008";
    public static String yunGetCartDesKey="12345678";
    public static final String YZ_SINGDES = "@#$%123!XCVDWF12feasctes";
    public static final String YZ_DATADES = "@Tdsasdf@#$!!$21agesv25s";

    /**
     * 正式服务器地址
     */
    public static String yunGetUserDetail = "http://api.ipaye.cn/api/v2/user/getDetail";//云支付获取帐户信息
    public static String yunMerchantIdentifyUrl = "http://api.ipaye.cn/api/v2/user/addFlagPos";//设置pos机商户标识
    public static String cancelYunMerchantIdentifyUrl = "http://api.ipaye.cn/api/v2/user/cancelFlagPos";//取消pos机商户标识
    public static String yunCheckUrl="http://api.ipaye.cn/api/v2/yzshoppinglog/sumYesterday";//对账
    public static String yunReturnPledgeUrl="http://api.ipaye.cn/api/v2/payorder/yzReturnPledge";

//    public static String yunGetUserDetail = "http://192.168.1.31:9100/yunpay_service/api/v2/user/getDetail";//云支付获取帐户信息
//    public static String yunMerchantIdentifyUrl = "http://192.168.1.31:9100/yunpay_service/api/v2/user/addFlagPos";//设置pos机商户标识
//    public static String yunCheckUrl="http://192.168.1.31:9100/yunpay_service/api/v2/yzshoppinglog/sumYesterday";//对账
//    public static String cancelYunMerchantIdentifyUrl = "http://192.168.1.31:9100/yunpay_service/api/v2/user/cancelFlagPos";//取消pos机商户标识
//    public static String yunReturnPledgeUrl="http://192.168.1.31:9100/yunpay_service/api/v2/payorder/yzReturnPledge";//退押金接口

    /**
     * 下发配置
     */
    public static String apilklSplit="https://api.lakala.com/thirdpartplatform/merchmanage/9202.dor";
    public static String apilklShaKey="YPYvd1SEqbLzrBY5JF9J8rJcPNvZJWkQ";
    public static String compOrgCode="YZJG";
    public static String funCode="9202";

    /**
     *拉卡拉业务单ftp配置
     */
    public static String ip = "139.224.17.170"; // 服务器IP地址
    public static String userName = "yzpos2"; // 用户名
    public static String userPwd = "CUvzFyL3UaceRb82"; // 密码
    public static int port = 21; // 端口号
    public static String path = "/lkl/"; // 读取文件的存放目录
    public static String path_t = "/lkl_platform/"; // 读取文件的存放目录
    public static String  strencoding = "UTF-8"; // 设置编码
    public static String file_h = "TF_258711_";//账单名
    public static String file_tixian = "LKL_YZF_TIXIAN_DATA_";//账单名
    public static String file_txn = "LKL_YZF_TXN_DATA_";//账单名
    //代理人收益
    public static double auIncomeScale = 0.0005; // 万分之五
    //注册方式
    public static String regWay = "4";

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
     * 用户刷卡金额限制
     */
    public static java.util.HashMap <String,DevicePayCardQuota> devicePayCardQuotaMapList = new java.util.HashMap();
}
