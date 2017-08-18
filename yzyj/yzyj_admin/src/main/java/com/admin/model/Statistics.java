package com.admin.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/2/14.
 */
public class Statistics {
    private long yzCount;
    private long yzCountQFZY;
    private long agentCount;
    private long skCount;
    private int skCountBS;
    private long smCount;
    private int smCountBS;
    private String dayTime;
    private String monthTime;
    private int type;
    private Date addTime;
    private long moneyCount;
    private int bsCount;
    private int typeCount;
    private String statisticsTime;

    private long agentDeviceAllCountMoney;//代理人设备总收益
    private long agentDeviceMonthCountMoney;//代理人设备当月总收益
    private int agentDeviceCount;//代理人设备总台数
    private int agentDeviceMonthCount;//代理人设备当月新增台数
    private String agentUnique;//代理人唯一性

    public String getAgentUnique() {
        return agentUnique;
    }

    public void setAgentUnique(String agentUnique) {
        this.agentUnique = agentUnique;
    }

    public long getAgentDeviceAllCountMoney() {
        return agentDeviceAllCountMoney;
    }

    public void setAgentDeviceAllCountMoney(long agentDeviceAllCountMoney) {
        this.agentDeviceAllCountMoney = agentDeviceAllCountMoney;
    }

    public long getAgentDeviceMonthCountMoney() {
        return agentDeviceMonthCountMoney;
    }

    public void setAgentDeviceMonthCountMoney(long agentDeviceMonthCountMoney) {
        this.agentDeviceMonthCountMoney = agentDeviceMonthCountMoney;
    }

    public int getAgentDeviceCount() {
        return agentDeviceCount;
    }

    public void setAgentDeviceCount(int agentDeviceCount) {
        this.agentDeviceCount = agentDeviceCount;
    }

    public int getAgentDeviceMonthCount() {
        return agentDeviceMonthCount;
    }

    public void setAgentDeviceMonthCount(int agentDeviceMonthCount) {
        this.agentDeviceMonthCount = agentDeviceMonthCount;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public long getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(long moneyCount) {
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

    public String getStatisticsTime() {
        return statisticsTime;
    }

    public void setStatisticsTime(String statisticsTime) {
        this.statisticsTime = statisticsTime;
    }

    public long getYzCount() {
        return yzCount;
    }

    public void setYzCount(long yzCount) {
        this.yzCount = yzCount;
    }

    public long getYzCountQFZY() {
        return yzCountQFZY;
    }

    public void setYzCountQFZY(long yzCountQFZY) {
        this.yzCountQFZY = yzCountQFZY;
    }

    public long getAgentCount() {
        return agentCount;
    }

    public void setAgentCount(long agentCount) {
        this.agentCount = agentCount;
    }

    public long getSkCount() {
        return skCount;
    }

    public void setSkCount(long skCount) {
        this.skCount = skCount;
    }

    public int getSkCountBS() {
        return skCountBS;
    }

    public void setSkCountBS(int skCountBS) {
        this.skCountBS = skCountBS;
    }

    public long getSmCount() {
        return smCount;
    }

    public void setSmCount(long smCount) {
        this.smCount = smCount;
    }

    public int getSmCountBS() {
        return smCountBS;
    }

    public void setSmCountBS(int smCountBS) {
        this.smCountBS = smCountBS;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public String getMonthTime() {
        return monthTime;
    }

    public void setMonthTime(String monthTime) {
        this.monthTime = monthTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
