package com.admin.model;

/**
 * Created by Administrator on 2017-05-03.
 */
public class PosWithdrawRecordExcel {
    private String yftNumber;
    private String loginName;
    private Integer amount;
    private String bankAccount;
    private String accountName;
    private String bankName;
    private String withdrawTime;
    private String auditTime;
    private String isSendTime;
    private String isSendMoney;
    private String orderLogNo;//下发流水账
    private String lklMerchantCode;//设备商户号
    private String lklTerminalCode;//设备终端号

    public String getLklMerchantCode() {
        return lklMerchantCode;
    }

    public void setLklMerchantCode(String lklMerchantCode) {
        this.lklMerchantCode = lklMerchantCode;
    }

    public String getLklTerminalCode() {
        return lklTerminalCode;
    }

    public void setLklTerminalCode(String lklTerminalCode) {
        this.lklTerminalCode = lklTerminalCode;
    }

    public String getOrderLogNo() {
        return orderLogNo;
    }

    public void setOrderLogNo(String orderLogNo) {
        this.orderLogNo = orderLogNo;
    }

    public String getYftNumber() {
        return yftNumber;
    }

    public void setYftNumber(String yftNumber) {
        this.yftNumber = yftNumber;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIsSendTime() {
        return isSendTime;
    }

    public void setIsSendTime(String isSendTime) {
        this.isSendTime = isSendTime;
    }

    public String getIsSendMoney() {
        return isSendMoney;
    }

    public void setIsSendMoney(String isSendMoney) {
        this.isSendMoney = isSendMoney;
    }
}
