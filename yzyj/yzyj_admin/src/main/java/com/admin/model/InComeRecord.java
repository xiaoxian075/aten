package com.admin.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/9.
 */
public class InComeRecord {
    private int id;
    private int money;
    private Date time;
    private String agentUnique;
    private String agentName;
    private String agentYPLoginName;
    private String checkDay;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sdate;    //支付开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date edate;    //支付结束时间
    private String type;
    private Integer agentMonthCount;
    private String strSdate;
    private String strEdate;

    public String getStrSdate() {
        return strSdate;
    }

    public void setStrSdate(String strSdate) {
        this.strSdate = strSdate;
    }

    public String getStrEdate() {
        return strEdate;
    }

    public void setStrEdate(String strEdate) {
        this.strEdate = strEdate;
    }

    public Integer getAgentMonthCount() {
        return agentMonthCount;
    }

    public void setAgentMonthCount(Integer agentMonthCount) {
        this.agentMonthCount = agentMonthCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCheckDay() {
        return checkDay;
    }

    public void setCheckDay(String checkDay) {
        this.checkDay = checkDay;
    }

    public Date getSdate() {
        return sdate;
    }

    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAgentUnique() {
        return agentUnique;
    }

    public void setAgentUnique(String agentUnique) {
        this.agentUnique = agentUnique;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentYPLoginName() {
        return agentYPLoginName;
    }

    public void setAgentYPLoginName(String agentYPLoginName) {
        this.agentYPLoginName = agentYPLoginName;
    }
}
