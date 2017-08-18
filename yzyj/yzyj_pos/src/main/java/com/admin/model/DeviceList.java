package com.admin.model;

import java.util.Date;

/**
 * Created by Administrator on 2016/10/20.
 */
public class DeviceList{
    private String id;    //sys_id
    private String deviceUnique;
    private Date createTime;    //代理ID
    private String agentUnique;    //代理ID
    private String agentName;    //代理名
    private String deviceCode;    //设备编码
    private String activationCode;    //激活码
    private String deviceName;    //设备名称
    private Integer deviceType;    //设备类型
    private String merchantName;    //商家姓名
    private String merchantYunPayAccount;    //商家云支付账号
    private String merchantPhone;    //商家联系电话
    private String state;    //状态
    private String realName; // 商户名
    private String moneyCount;//历史累计总额
    private String dayCount;//当日交易总和
    private String machineCode;
    private String lklMachineCode;
    private String lklActivationCode;
    private String lklMerchantCode;//拉卡拉商户号
    private String lklTerminalCode;//拉卡拉终端号
    private int quotaGroup;//额度
    private int approvalStatus;//状态
    private String merchantId;
    private int qid;
    private String strCreateTime;
    private int subsidyStatus;//返现状态     0：未提交 1：待审批  2：审批通过  3：不通过
    private String subsidyTime;    //返现时间

    public String getSubsidyTime() {
        return subsidyTime;
    }

    public void setSubsidyTime(String subsidyTime) {
        this.subsidyTime = subsidyTime;
    }

    public int getSubsidyStatus() {
        return subsidyStatus;
    }

    public void setSubsidyStatus(int subsidyStatus) {
        this.subsidyStatus = subsidyStatus;
    }

    public String getStrCreateTime() {
        return strCreateTime;
    }

    public void setStrCreateTime(String strCreateTime) {
        this.strCreateTime = strCreateTime;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public int getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(int approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public int getQuotaGroup() {
        return quotaGroup;
    }

    public void setQuotaGroup(int quotaGroup) {
        this.quotaGroup = quotaGroup;
    }

    public String getLklMerchantCode() {

        return lklMerchantCode;
    }

    public String getDayCount() {
        return dayCount;
    }

    public void setDayCount(String dayCount) {
        this.dayCount = dayCount;
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

    public String getLklMachineCode() {
        return lklMachineCode;
    }

    public void setLklMachineCode(String lklMachineCode) {
        this.lklMachineCode = lklMachineCode;
    }

    public String getLklActivationCode() {
        return lklActivationCode;
    }

    public void setLklActivationCode(String lklActivationCode) {
        this.lklActivationCode = lklActivationCode;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(String moneyCount) {
        this.moneyCount = moneyCount;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }
}
