package com.aten.model.bean;

/**
 * Created by 陈熠
 * 2017/7/24.
 */
public class JPushBean {
    private String alias;	//别名，设备的唯一值
    private String title;	//标题
    private String content;	//内容	
    private String id;		//订单号
    private String type;	//0
    private String userId;	//sessionid
    public JPushBean() {
    }
    public JPushBean(String alias, String title, String content, String id, String type,String userId) {
        this.alias = alias;
        this.title = title;
        this.content = content;
        this.id = id;
        this.type = type;
        this.userId=userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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


}
