package com.admin.model;

/**
 * Created by Administrator on 2016/12/9.
 */
public class OrderDetail {
    private String payTime;
    private Integer count;
    private String time;
    private String type;
    private Integer dayMoney;
    private String dayMoneyStr;

    public String getDayMoneyStr() {
        return dayMoneyStr;
    }

    public void setDayMoneyStr(String dayMoneyStr) {
        this.dayMoneyStr = dayMoneyStr;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDayMoney() {
        return dayMoney;
    }

    public void setDayMoney(Integer dayMoney) {
        this.dayMoney = dayMoney;
    }
}
