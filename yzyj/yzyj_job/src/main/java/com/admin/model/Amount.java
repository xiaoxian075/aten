package com.admin.model;

/**
 * Created by Administrator on 2016/11/24.
 */
public class Amount {
    private int id;
    private String deviceCode;

    private int amount;
    private int smAmount;
    private int scxkAmount;
    private int sxykAmount;

    private int count;
    private int smCount;
    private int scxkCount;
    private int sxykCount;
    private String payType;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    private String dayCountAmount;


    public String getDayCountAmount() {
        return dayCountAmount;
    }

    public void setDayCountAmount(String dayCountAmount) {
        this.dayCountAmount = dayCountAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getSmAmount() {
        return smAmount;
    }

    public void setSmAmount(int smAmount) {
        this.smAmount = smAmount;
    }

    public int getScxkAmount() {
        return scxkAmount;
    }

    public void setScxkAmount(int scxkAmount) {
        this.scxkAmount = scxkAmount;
    }

    public int getSxykAmount() {
        return sxykAmount;
    }

    public void setSxykAmount(int sxykAmount) {
        this.sxykAmount = sxykAmount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSmCount() {
        return smCount;
    }

    public void setSmCount(int smCount) {
        this.smCount = smCount;
    }

    public int getScxkCount() {
        return scxkCount;
    }

    public void setScxkCount(int scxkCount) {
        this.scxkCount = scxkCount;
    }

    public int getSxykCount() {
        return sxykCount;
    }

    public void setSxykCount(int sxykCount) {
        this.sxykCount = sxykCount;
    }
}
