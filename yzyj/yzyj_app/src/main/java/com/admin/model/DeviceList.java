package com.admin.model;

import java.util.Date;

/**
 * Created by wjf on 2016/10/20.
 */
public class DeviceList {
    private String id;    //sys_id
    private String deviceUnique;
    private Date createTime;    //代理ID
    private String agentUnique;    //代理ID
    private String agentName;    //代理名
    private String deviceCode;    //设备编码
    private String activationCode;    //激活码
    private String deviceName;    //设备名称
    private Integer deviceType;    //设备类型

    private String merchantName;
    private String merchantYunPayAccount;    //商家云支付账号
    private String merchantPhone;    //商家联系电话
    private String state;    //状态
    private Integer History;    //状态
    private Integer moneyCount;//历史累计总额
    private Integer dayCount;//当日交易总和
    private String machineCode;
    private String lklMerchantCode;//商户号
    private String lklMerminalCode;//终端号

    private Integer mDayCount;//商户当天统计
    private Integer mMonthCount;//商户当月统计
    private Integer mHistoryCount;//商户累计统计

    public String getLklMerminalCode() {
        return lklMerminalCode;
    }

    public void setLklMerminalCode(String lklMerminalCode) {
        this.lklMerminalCode = lklMerminalCode;
    }

    public String getLklMerchantCode() {
        return lklMerchantCode;
    }

    public void setLklMerchantCode(String lklMerchantCode) {
        this.lklMerchantCode = lklMerchantCode;
    }

    public Integer getmDayCount() {
        return mDayCount;
    }

    public void setmDayCount(Integer mDayCount) {
        this.mDayCount = mDayCount;
    }

    public Integer getmMonthCount() {
        return mMonthCount;
    }

    public void setmMonthCount(Integer mMonthCount) {
        this.mMonthCount = mMonthCount;
    }

    public Integer getmHistoryCount() {
        return mHistoryCount;
    }

    public void setmHistoryCount(Integer mHistoryCount) {
        this.mHistoryCount = mHistoryCount;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public Integer getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(Integer moneyCount) {
        this.moneyCount = moneyCount;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }

    public Integer getHistory() {
        return History;
    }

    public void setHistory(Integer history) {
        History = history;
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

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgentUnique() {
        return agentUnique;
    }

    public void setAgentUnique(String agentUnique) {
        this.agentUnique = agentUnique;
    }

    public String getDeviceUnique() {
        return deviceUnique;
    }

    public void setDeviceUnique(String deviceUnique) {
        this.deviceUnique = deviceUnique;
    }

}
