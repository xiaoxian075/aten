package com.admin.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/3/30.
 */
public class Txn{
    private String id;
    private String txnDate;//分账日期
    private String txnVirtualAccount;//虚拟账户余额
    private String txnCountBs;//交易笔数
    private String txnJyMoney;//交易总金额
    private String txnRzMoney;//入账总金额
    private String txnHbMoney;//红包总金额
    private String txnBfjMoney;//备付金余额
    private Date updateTime;//更新时间
    private String sdate;    //分账开始时间
    private String edate;    //分账结束时间
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

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getTxnVirtualAccount() {
        return txnVirtualAccount;
    }

    public void setTxnVirtualAccount(String txnVirtualAccount) {
        this.txnVirtualAccount = txnVirtualAccount;
    }

    public String getTxnCountBs() {
        return txnCountBs;
    }

    public void setTxnCountBs(String txnCountBs) {
        this.txnCountBs = txnCountBs;
    }

    public String getTxnJyMoney() {
        return txnJyMoney;
    }

    public void setTxnJyMoney(String txnJyMoney) {
        this.txnJyMoney = txnJyMoney;
    }

    public String getTxnRzMoney() {
        return txnRzMoney;
    }

    public void setTxnRzMoney(String txnRzMoney) {
        this.txnRzMoney = txnRzMoney;
    }

    public String getTxnHbMoney() {
        return txnHbMoney;
    }

    public void setTxnHbMoney(String txnHbMoney) {
        this.txnHbMoney = txnHbMoney;
    }

    public String getTxnBfjMoney() {
        return txnBfjMoney;
    }

    public void setTxnBfjMoney(String txnBfjMoney) {
        this.txnBfjMoney = txnBfjMoney;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
