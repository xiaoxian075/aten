package com.admin.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/1/14.
 */
public class AttractMerchants {
    private String id;    //id
    private String title;    //标题
    private String note;    //说明
    private String body;    //内容
    private java.util.Date createTime;    //创建时间
    private java.util.Date lastTime;    //修改时间
    private Integer type;    //类型
    private Integer px;    //排序

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPx() {
        return px;
    }

    public void setPx(Integer px) {
        this.px = px;
    }
}
