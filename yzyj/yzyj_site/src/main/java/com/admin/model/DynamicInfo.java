package com.admin.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/2.
 */
public class DynamicInfo implements Serializable {
    private String id;    //id
    private String title;    //标题
    private String body;    //内容
    private java.util.Date createTime;    //创建时间
    private Integer type;    //类型
    private Integer px;    //排序
    private String img;    //内容

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
