package com.admin.model;

/**
 * Created by Administrator on 2017-04-07.
 */
public class DeviceListExcel {
    private String realName; // 商户名
    private String lklMerchantCode;//拉卡拉商户号
    private String lklTerminalCode;//拉卡拉终端号
    private String merchantName;    //商家姓名
    private String merchantYunPayAccount;    //商家云支付账号
    private String subsidyTime;    //返现时间

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getLklMerchantCode() {
        return lklMerchantCode;
    }

    public void setLklMerchantCode(String lklMerchantCode) {
        this.lklMerchantCode = lklMerchantCode;
    }

    public String getLklTerminalCode() {
        return lklTerminalCode;
    }

    public void setLklTerminalCode(String lklTerminalCode) {
        this.lklTerminalCode = lklTerminalCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantYunPayAccount() {
        return merchantYunPayAccount;
    }

    public void setMerchantYunPayAccount(String merchantYunPayAccount) {
        this.merchantYunPayAccount = merchantYunPayAccount;
    }

    public String getSubsidyTime() {
        return subsidyTime;
    }

    public void setSubsidyTime(String subsidyTime) {
        this.subsidyTime = subsidyTime;
    }
}
