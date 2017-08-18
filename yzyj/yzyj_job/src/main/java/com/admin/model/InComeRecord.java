package com.admin.model;

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
    private String type;

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
