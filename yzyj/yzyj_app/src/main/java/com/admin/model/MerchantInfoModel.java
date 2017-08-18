package com.admin.model;

/**
 * Created by Administrator on 2016/12/14.
 */
public class MerchantInfoModel {
    private String name;
    private String time;
    private String dayMoney;
    private String myId;
    private String monthMoney;
    private String historyMoney;
    private String mPhone;
    private String merchantCode;

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDayMoney() {
        return dayMoney;
    }

    public void setDayMoney(String dayMoney) {
        this.dayMoney = dayMoney;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getMonthMoney() {
        return monthMoney;
    }

    public void setMonthMoney(String monthMoney) {
        this.monthMoney = monthMoney;
    }

    public String getHistoryMoney() {
        return historyMoney;
    }

    public void setHistoryMoney(String historyMoney) {
        this.historyMoney = historyMoney;
    }
}
