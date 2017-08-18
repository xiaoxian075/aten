package com.admin.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Administrator on 2017/1/12.
 */
public class PosWithdrawRecord {
    private int pid;
    private int id;
    private int auditStatus;
    private String loginName;
    private Integer amount;
    private String auditTime;
    private String withdrawTime;
    private String accountName;
    private String bankAccount;
    private String withdrawNote;
    private String bankName;
    private int isSend;
    private Date isSendTime;
    private String isSendMoney;

    public int getIsSend() {
        return isSend;
    }

    public void setIsSend(int isSend) {
        this.isSend = isSend;
    }

    public Date getIsSendTime() {
        return isSendTime;
    }

    public void setIsSendTime(Date isSendTime) {
        this.isSendTime = isSendTime;
    }

    public String getIsSendMoney() {
        return isSendMoney;
    }

    public void setIsSendMoney(String isSendMoney) {
        this.isSendMoney = isSendMoney;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sdate;    //开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date edate;    //结束时间

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

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getWithdrawTime() {
        return withdrawTime;
    }

    public void setWithdrawTime(String withdrawTime) {
        this.withdrawTime = withdrawTime;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getWithdrawNote() {
        return withdrawNote;
    }

    public void setWithdrawNote(String withdrawNote) {
        this.withdrawNote = withdrawNote;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

}
