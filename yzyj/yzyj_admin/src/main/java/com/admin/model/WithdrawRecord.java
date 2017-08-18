package com.admin.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/15.
 */
public class WithdrawRecord extends CommModel{
    private int id;
    private String agentUnique;
    private String orderNumber;
    private Date createTime;
    private int totalFee;
    private int type;
    private int state;
    private String note;
    private String realName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sdate;    //支付开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date edate;    //支付结束时间
    private String yunPayLoginName;

    public String getYunPayLoginName() {
        return yunPayLoginName;
    }

    public void setYunPayLoginName(String yunPayLoginName) {
        this.yunPayLoginName = yunPayLoginName;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
