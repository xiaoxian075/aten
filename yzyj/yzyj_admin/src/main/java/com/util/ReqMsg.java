package com.util;

import java.io.Serializable;

public class  ReqMsg<T> implements Serializable{
    private static final long serialVersionUID = 4594323480487971034L;
    private int code;
    private String desc;
    private T info;
    public ReqMsg() {
        super();
    }
    public ReqMsg(int code, String desc, T info) {
        super();
        this.code = code;
        this.desc = desc;
        this.info = info;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public T getInfo() {
        return info;
    }
    public void setInfo(T info) {
        this.info = info;
    }

}

