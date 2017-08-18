package com.inter;

import java.io.Serializable;

public class ReqMsg<T> implements Serializable{
    private static final long serialVersionUID = 4594323480487971034L;
    private int code;
    private String desc;
    private T t;
    public ReqMsg() {
        super();
    }
    public ReqMsg(int code, String desc, T t) {
        super();
        this.code = code;
        this.desc = desc;
        this.t = t;
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
	public T getT() {
		return t;
	}
	public void setT(T t) {
		this.t = t;
	}

}

//package com.aten.yzyj.inter;
//
//import java.io.Serializable;
//
//public class  ReqMsg implements Serializable{
//    private static final long serialVersionUID = 4594323480487971034L;
//    private int code;
//    private String desc;
//    private Object obj;
//    public ReqMsg() {
//        super();
//    }
//    public ReqMsg(int code, String desc, Object obj) {
//        super();
//        this.code = code;
//        this.desc = desc;
//        this.obj = obj;
//    }
//    public int getCode() {
//        return code;
//    }
//    public void setCode(int code) {
//        this.code = code;
//    }
//    public String getDesc() {
//        return desc;
//    }
//    public void setDesc(String desc) {
//        this.desc = desc;
//    }
//	public Object getObj() {
//		return obj;
//	}
//	public void setObj(Object obj) {
//		this.obj = obj;
//	}
//
//}

