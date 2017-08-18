package com.admin.model;

/**
 * Created by Administrator on 2017-04-20.
 */
public class OrderPosDetail {
    private String payTime;
    private Integer count;
    private String time;
    private String type;
    private Integer dayMoney;
    private String dayMoneyStr;
    private String orderNumber;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    public String getDayMoneyStr() {
        return dayMoneyStr;
    }

    public void setDayMoneyStr(String dayMoneyStr) {
        this.dayMoneyStr = dayMoneyStr;
    }
}
