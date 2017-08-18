package com.admin.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by wjf on 2016/10/7.
 */
public class DeviceInfo {
    private String deviceUnique;
    private String machineCode;
    private String activationCode;
    private String pwd ;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date lastTime;
    private String token;
    private String lon;
    private String lat;
    private String loginType;
    private String device;
    private String deviceNo;
    private String ip;
    private Integer state;
    private String note;
    private String yunId;
    private Integer yunIdLev;
    private String merchantYunId;
    private String cardType;
    private String agentUnique;
    private String umengToken;
    private String listState;
    private String quotaGroup;
    private String merchantName;
    private String deviceName;
    private String lklMerchantCode;
    private String lklTerminalCode;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getYunId() {
        return yunId;
    }

    public void setYunId(String yunId) {
        this.yunId = yunId;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Integer getYunIdLev() {
        return yunIdLev;
    }

    public void setYunIdLev(Integer yunIdLev) {
        this.yunIdLev = yunIdLev;
    }

    public String getMerchantYunId() {
        return merchantYunId;
    }

    public void setMerchantYunId(String merchantYunId) {
        this.merchantYunId = merchantYunId;
    }

    public String getDeviceUnique() {
        return deviceUnique;
    }

    public void setDeviceUnique(String deviceUnique) {
        this.deviceUnique = deviceUnique;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getAgentUnique() {
        return agentUnique;
    }

    public void setAgentUnique(String agentUnique) {
        this.agentUnique = agentUnique;
    }

    public String getUmengToken() {
        return umengToken;
    }

    public void setUmengToken(String umengToken) {
        this.umengToken = umengToken;
    }



    public String getListState() {
        return listState;
    }

    public void setListState(String listState) {
        this.listState = listState;
    }

    public String getQuotaGroup() {
        return quotaGroup;
    }

    public void setQuotaGroup(String quotaGroup) {
        this.quotaGroup = quotaGroup;
    }
}
