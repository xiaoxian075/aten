package com.admin.model;
import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection="table_info")
public class MongoTest implements Serializable,Cloneable{
    /**
     *
     */
    private static final long serialVersionUID = 2638049111041420021L;
    /**
     * 主键
     */
    @Id
    private String id;
    /**
     *
     */
    @JSONField(serialize=false)
//	@Indexed
    private String loginName;
    /**
     *当前用户名
     */
//	@Indexed
    private String userName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 备注
     */
    @JSONField(serialize=false)
    private String nick;
    /**
     *  头像
     */
    @JSONField(serialize=false)
    private String picHead;
    //	@Transient
    @JSONField(name="picHead")
    private String npHead;

    /**
     * 已经同步到环信IM标志
     */
    @JSONField(serialize=false)
    private Integer hasyn;

    public String getShowName(){
        String name=(this.nick!=null&&!this.userName.equals(this.nick))?this.nick:(this.nickName!=null?this.nickName:"云粉");
        return name;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getPicHead() {
        return picHead;
    }
    public void setPicHead(String picHead) {
        this.picHead = picHead;
    }
    public String getNick() {
        return nick;
    }
    public void setNick(String nick) {
        this.nick = nick;
    }
    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public MongoTest clone() throws CloneNotSupportedException{
        return (MongoTest) super.clone();
    }
    public String getNpHead() {
        if(picHead==null||picHead.length()<=0) return null;
        return ""+picHead;
    }

    public void setNpHead(String npHead) {
        this.npHead = npHead;
    }

    public Integer getHasyn() {
        return hasyn;
    }

    public void setHasyn(Integer hasyn) {
        this.hasyn = hasyn;
    }

}
