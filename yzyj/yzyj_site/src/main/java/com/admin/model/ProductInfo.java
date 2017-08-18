package com.admin.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/2.
 */
public class ProductInfo implements Serializable {
    private String id;    //id
    private String title;    //标题
    private String body;    //内容
    private Date createTime;    //创建时间
    private Integer type;    //类型
    private Integer px;    //排序
    private String img;    //内容
    private String synopsis; //简介
    private String money;//金额
    private String sub_img;//列表页小图
    private String[] str_img;//轮播组数

    public String[] getStr_img() {
        return str_img;
    }

    public void setStr_img(String[] str_img) {
        this.str_img = str_img;
    }

    public String getSub_img() {
        return sub_img;
    }

    public void setSub_img(String sub_img) {
        this.sub_img = sub_img;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

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
