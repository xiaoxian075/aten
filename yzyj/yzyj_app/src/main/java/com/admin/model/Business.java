package com.admin.model;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/30.
 */
public class Business{
    private int id;
    private String merchantNo;
    private String merchantName;
    private String merchantAddress;
    private String merchantLegalPerson;
    private String merchantIdentityCard;
    private String businessLicense;
    private String merchantPersonName;
    private String merchantPersonPhone;
    private String email;
    private String accountNumber;
    private String accountName;
    private String openBank;
    private String yunPayAccount;
    private int status;
    private Date createTime;
    private String followUpName;
    private String agentUnique;
    private String agencyName;

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getAgentUnique() {
        return agentUnique;
    }

    public void setAgentUnique(String agentUnique) {
        this.agentUnique = agentUnique;
    }

    public String getFollowUpName() {
        return followUpName;
    }

    public void setFollowUpName(String followUpName) {
        this.followUpName = followUpName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getYunPayAccount() {
        return yunPayAccount;
    }

    public void setYunPayAccount(String yunPayAccount) {
        this.yunPayAccount = yunPayAccount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public String getMerchantLegalPerson() {
        return merchantLegalPerson;
    }

    public void setMerchantLegalPerson(String merchantLegalPerson) {
        this.merchantLegalPerson = merchantLegalPerson;
    }

    public String getMerchantIdentityCard() {
        return merchantIdentityCard;
    }

    public void setMerchantIdentityCard(String merchantIdentityCard) {
        this.merchantIdentityCard = merchantIdentityCard;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getMerchantPersonName() {
        return merchantPersonName;
    }

    public void setMerchantPersonName(String merchantPersonName) {
        this.merchantPersonName = merchantPersonName;
    }

    public String getMerchantPersonPhone() {
        return merchantPersonPhone;
    }

    public void setMerchantPersonPhone(String merchantPersonPhone) {
        this.merchantPersonPhone = merchantPersonPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }
}
