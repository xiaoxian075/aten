package com.admin.model;

/**
 * Created by Administrator on 2016/12/9.
 */
public class DeviceDetailModel {
    private String deviceUnique;
    private String createTime;    //代理ID
    private String agentUnique;    //代理ID
    private String agentName;    //代理名
    private String deviceCode;    //设备编码
    private String activationCode;    //激活码
    private String deviceName;    //设备名称

    private String merchantName;
    private String merchantYunPayAccount;    //商家云支付账号
    private String merchantPhone;    //商家联系电话
    private String state;    //状态
    private String moneyCount;//历史累计总额
    private String dayCount;//当日交易总和

    private String lklMerchantCode;//商户号
    private String lklMerminalCode;//终端号
    private String machineCode;//设备编码

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getLklMerchantCode() {
        return lklMerchantCode;
    }

    public void setLklMerchantCode(String lklMerchantCode) {
        this.lklMerchantCode = lklMerchantCode;
    }

    public String getLklMerminalCode() {
        return lklMerminalCode;
    }

    public void setLklMerminalCode(String lklMerminalCode) {
        this.lklMerminalCode = lklMerminalCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getDeviceUnique() {
        return deviceUnique;
    }

    public void setDeviceUnique(String deviceUnique) {
        this.deviceUnique = deviceUnique;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAgentUnique() {
        return agentUnique;
    }

    public void setAgentUnique(String agentUnique) {
        this.agentUnique = agentUnique;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


    public String getMerchantYunPayAccount() {
        return merchantYunPayAccount;
    }

    public void setMerchantYunPayAccount(String merchantYunPayAccount) {
        this.merchantYunPayAccount = merchantYunPayAccount;
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(String moneyCount) {
        this.moneyCount = moneyCount;
    }

    public String getDayCount() {
        return dayCount;
    }

    public void setDayCount(String dayCount) {
        this.dayCount = dayCount;
    }

}
