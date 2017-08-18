package com.admin.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/3/30.
 */
public class TiXian{
    private String id;
    private String tixianDate;//提现日期
    private String tixianMoney;//提现总金额
    private String tixianCountBs;//提现笔数
    private String tixianPtMoney;//平台到账总金额
    private String tixianShMoney;//商户到账总金额
    private Date updateTime;//更新时间
    private String sdate;    //提现开始时间
    private String edate;    //提现结束时间
    private String reloadDay;    //载入时间

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public String getReloadDay() {
        return reloadDay;
    }

    public void setReloadDay(String reloadDay) {
        this.reloadDay = reloadDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTixianDate() {
        return tixianDate;
    }

    public void setTixianDate(String tixianDate) {
        this.tixianDate = tixianDate;
    }

    public String getTixianMoney() {
        return tixianMoney;
    }

    public void setTixianMoney(String tixianMoney) {
        this.tixianMoney = tixianMoney;
    }

    public String getTixianCountBs() {
        return tixianCountBs;
    }

    public void setTixianCountBs(String tixianCountBs) {
        this.tixianCountBs = tixianCountBs;
    }

    public String getTixianPtMoney() {
        return tixianPtMoney;
    }

    public void setTixianPtMoney(String tixianPtMoney) {
        this.tixianPtMoney = tixianPtMoney;
    }

    public String getTixianShMoney() {
        return tixianShMoney;
    }

    public void setTixianShMoney(String tixianShMoney) {
        this.tixianShMoney = tixianShMoney;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
