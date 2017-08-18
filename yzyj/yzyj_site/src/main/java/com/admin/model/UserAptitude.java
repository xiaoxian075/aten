package com.admin.model;

import java.util.Date;

/**
 * Created by Administrator on 2016/9/1.
 */
public class UserAptitude {
    private String sysId;
    private String purchaseId;
    private String name;
    private String aptitudeNumber;
    private String numberType;
    private Date buyTime;
    private String status;
    private String mobile;
    private String orderId;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAptitudeNumber() {
        return aptitudeNumber;
    }

    public void setAptitudeNumber(String aptitudeNumber) {
        this.aptitudeNumber = aptitudeNumber;
    }

    public String getNumberType() {
        return numberType;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }
}
