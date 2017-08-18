package com.admin.model;

/**
 * Created by Administrator on 2016/11/29.
 */
public class Quota extends  CommModel{
    private int id;
    private int quotaGroup;
    private String deviceUnique;
    private long cxkOne;
    private long cxkDay;
    private long cxkMonth;
    private long xykOne;
    private long xykDay;
    private long xykMonth;
    private long xykExcessOne;
    private long xykExcessDay;
    private long xykExcessMonth;
    private long cxkExcessOne;
    private long cxkExcessDay;
    private long cxkExcessMonth;
    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuotaGroup() {
        return quotaGroup;
    }

    public void setQuotaGroup(int quotaGroup) {
        this.quotaGroup = quotaGroup;
    }

    public String getDeviceUnique() {
        return deviceUnique;
    }

    public void setDeviceUnique(String deviceUnique) {
        this.deviceUnique = deviceUnique;
    }

    public long getCxkOne() {
        return cxkOne;
    }

    public void setCxkOne(long cxkOne) {
        this.cxkOne = cxkOne;
    }

    public long getCxkDay() {
        return cxkDay;
    }

    public void setCxkDay(long cxkDay) {
        this.cxkDay = cxkDay;
    }

    public long getCxkMonth() {
        return cxkMonth;
    }

    public void setCxkMonth(long cxkMonth) {
        this.cxkMonth = cxkMonth;
    }

    public long getXykOne() {
        return xykOne;
    }

    public void setXykOne(long xykOne) {
        this.xykOne = xykOne;
    }

    public long getXykDay() {
        return xykDay;
    }

    public void setXykDay(long xykDay) {
        this.xykDay = xykDay;
    }

    public long getXykMonth() {
        return xykMonth;
    }

    public void setXykMonth(long xykMonth) {
        this.xykMonth = xykMonth;
    }

    public long getXykExcessOne() {
        return xykExcessOne;
    }

    public void setXykExcessOne(long xykExcessOne) {
        this.xykExcessOne = xykExcessOne;
    }

    public long getXykExcessDay() {
        return xykExcessDay;
    }

    public void setXykExcessDay(long xykExcessDay) {
        this.xykExcessDay = xykExcessDay;
    }

    public long getXykExcessMonth() {
        return xykExcessMonth;
    }

    public void setXykExcessMonth(long xykExcessMonth) {
        this.xykExcessMonth = xykExcessMonth;
    }

    public long getCxkExcessOne() {
        return cxkExcessOne;
    }

    public void setCxkExcessOne(long cxkExcessOne) {
        this.cxkExcessOne = cxkExcessOne;
    }

    public long getCxkExcessDay() {
        return cxkExcessDay;
    }

    public void setCxkExcessDay(long cxkExcessDay) {
        this.cxkExcessDay = cxkExcessDay;
    }

    public long getCxkExcessMonth() {
        return cxkExcessMonth;
    }

    public void setCxkExcessMonth(long cxkExcessMonth) {
        this.cxkExcessMonth = cxkExcessMonth;
    }
}
