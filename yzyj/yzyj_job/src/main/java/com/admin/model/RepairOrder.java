package com.admin.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/1/5.
 */
public class RepairOrder extends CommModel {
    private int id;
    private String orderNumber;
    private Date repairTime;
    private int alStatus;
    private String userName;
    private Date alTime;
    private Date lklPayTime;

    public Date getLklPayTime() {
        return lklPayTime;
    }

    public void setLklPayTime(Date lklPayTime) {
        this.lklPayTime = lklPayTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(Date repairTime) {
        this.repairTime = repairTime;
    }

    public int getAlStatus() {
        return alStatus;
    }

    public void setAlStatus(int alStatus) {
        this.alStatus = alStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getAlTime() {
        return alTime;
    }

    public void setAlTime(Date alTime) {
        this.alTime = alTime;
    }
}
