package com.admin.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/2/14.
 */
public class Statistics {
    private int yzCount;
    private int yzCountQFZY;
    private int agentCount;
    private int skCount;
    private int skCountBS;
    private int smCount;
    private int smCountBS;
    private Date addTime;
    private int moneyCount;
    private int bsCount;
    private int typeCount;
    private String statisticsTime;

    public String getStatisticsTime() {
        return statisticsTime;
    }

    public void setStatisticsTime(String statisticsTime) {
        this.statisticsTime = statisticsTime;
    }

    public int getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(int moneyCount) {
        this.moneyCount = moneyCount;
    }

    public int getBsCount() {
        return bsCount;
    }

    public void setBsCount(int bsCount) {
        this.bsCount = bsCount;
    }

    public int getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(int typeCount) {
        this.typeCount = typeCount;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public int getYzCount() {
        return yzCount;
    }

    public void setYzCount(int yzCount) {
        this.yzCount = yzCount;
    }

    public int getYzCountQFZY() {
        return yzCountQFZY;
    }

    public void setYzCountQFZY(int yzCountQFZY) {
        this.yzCountQFZY = yzCountQFZY;
    }

    public int getAgentCount() {
        return agentCount;
    }

    public void setAgentCount(int agentCount) {
        this.agentCount = agentCount;
    }

    public int getSkCount() {
        return skCount;
    }

    public void setSkCount(int skCount) {
        this.skCount = skCount;
    }

    public int getSkCountBS() {
        return skCountBS;
    }

    public void setSkCountBS(int skCountBS) {
        this.skCountBS = skCountBS;
    }

    public int getSmCount() {
        return smCount;
    }

    public void setSmCount(int smCount) {
        this.smCount = smCount;
    }

    public int getSmCountBS() {
        return smCountBS;
    }

    public void setSmCountBS(int smCountBS) {
        this.smCountBS = smCountBS;
    }
}
