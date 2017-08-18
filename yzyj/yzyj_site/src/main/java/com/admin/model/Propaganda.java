package com.admin.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/13.
 */
public class Propaganda implements Serializable {
    private String id;//视频ID
    private Integer type;//视频类型
    private String title;//视频标题
    private String content;//视频描述
    private String path;//视频路径
    private Date createTime;//创建时间
    private Integer status;//视频状态
    private Integer px;//排序
    private String picPath;//视频封面图

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
}
