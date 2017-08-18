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

    /**
     * 正式服务器地址
     */
//    public static String yunRechargeUrl = "http://api.ipaye.cn/api/v2/payorder/hardwareCloudRecharge";//云支付 , 充值入口
//    public static String yunCheckUrl="http://api.ipaye.cn/api/v2/yzshoppinglog/sumYesterday";//对账
//    public static String yunPosWithdrawUrl="http://api.ipaye.cn/api/v2/thirWithDrawBill/listWithDrawLog";//pos机商户提现

    public static String yunRechargeUrl = "http://192.168.1.31:7070/yunpay_service/api/v2/payorder/hardwareCloudRecharge";//云支付 , 充值入口
    public static String yunCheckUrl="http://192.168.1.31:7070/yunpay_service/api/v2/yzshoppinglog/sumYesterday";//对账
    public static String yunPosWithdrawUrl="http://192.168.1.31:7070/yunpay_service/api/v2/thirWithDrawBill/listWithDrawLog";//pos机商户提现

    /**
     * 拉卡拉资金拆分
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
    public static String  strencoding = "UTF-8"; // 设置编码
    public static String file_h = "TF_258711_";//账单名
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

    public static int yzSendMoney = 1000;//云智下发金额
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
