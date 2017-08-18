package com.admin.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/1.
 */
public class UserComment implements Serializable {
    private String sysId ;
    private String modelId ;
    private String modelName ;
    private String accountId ;
    private String accountName ;
    private String content ;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createDate ;
    private String accountPic;

    public String getAccountPic() {
        return accountPic;
    }

    public void setAccountPic(String accountPic) {
        this.accountPic = accountPic;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
