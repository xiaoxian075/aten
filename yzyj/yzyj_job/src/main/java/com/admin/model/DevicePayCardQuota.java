package com.admin.model;

/**
 * Created by wjf on 2016/11/24.
 */
public class DevicePayCardQuota {
    private Integer id;    //id
    private Integer quotaGroup;    //配额分组
    private String deviceUnique;    //设备编码
    private Integer cxkOne;    //储蓄卡单笔
    private Integer cxkDay;    //储蓄卡单日
    private Integer cxkMonth;    //储蓄卡单月
    private Integer xykOne;    //信用卡单笔
    private Integer xykDay;    //信用卡单日
    private Integer xykMonth;    //信用卡单月
    private Integer xykExcessOne;    //信用卡超额单笔
    private Integer xykExcessDay;    //信用卡超额单日
    private Integer xykExcessMonth;    //信用卡超额单日
    public Integer getXykExcessOne() {
        return xykExcessOne;
    }

    public void setXykExcessOne(Integer xykExcessOne) {
        this.xykExcessOne = xykExcessOne;
    }

    public Integer getXykExcessDay() {
        return xykExcessDay;
    }

    public void setXykExcessDay(Integer xykExcessDay) {
        this.xykExcessDay = xykExcessDay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuotaGroup() {
        return quotaGroup;
    }

    public void setQuotaGroup(Integer quotaGroup) {
        this.quotaGroup = quotaGroup;
    }

    public String getDeviceUnique() {
        return deviceUnique;
    }

    public void setDeviceUnique(String deviceUnique) {
        this.deviceUnique = deviceUnique;
    }

    public Integer getCxkOne() {
        return cxkOne;
    }

    public void setCxkOne(Integer cxkOne) {
        this.cxkOne = cxkOne;
    }

    public Integer getCxkDay() {
        return cxkDay;
    }

    public void setCxkDay(Integer cxkDay) {
        this.cxkDay = cxkDay;
    }

    public Integer getCxkMonth() {
        return cxkMonth;
    }

    public void setCxkMonth(Integer cxkMonth) {
        this.cxkMonth = cxkMonth;
    }

    public Integer getXykOne() {
        return xykOne;
    }

    public void setXykOne(Integer xykOne) {
        this.xykOne = xykOne;
    }

    public Integer getXykDay() {
        return xykDay;
    }

    public void setXykDay(Integer xykDay) {
        this.xykDay = xykDay;
    }

    public Integer getXykMonth() {
        return xykMonth;
    }

    public void setXykMonth(Integer xykMonth) {
        this.xykMonth = xykMonth;
    }

    public Integer getXykExcessMonth() {
        return xykExcessMonth;
    }

    public void setXykExcessMonth(Integer xykExcessMonth) {
        this.xykExcessMonth = xykExcessMonth;
    }
}
