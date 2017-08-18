package com.admin.model;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */
public class Navigation {
    private String id;
    private String logo;
    private String name;
    private String pid;
    private String url;
    private Integer status;
    private Integer px;
    private Date createTime;
    private List<Navigation> navigationList;

    public List<Navigation> getNavigationList() {
        return navigationList;
    }

    public void setNavigationList(List<Navigation> navigationList) {
        this.navigationList = navigationList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPx() {
        return px;
    }

    public void setPx(Integer px) {
        this.px = px;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
