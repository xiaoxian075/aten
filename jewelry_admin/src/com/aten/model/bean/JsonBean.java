package com.aten.model.bean;

import java.io.Serializable;

/**
 * @author linjunqin
 * @Description 封装json头信息
 * @date 2016-12-30 下午5:23:22
 */
public class JsonBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int is;
	private String msg;
	private Object data;

	public JsonBean(int is) {
		this.is = is;
	}

	public JsonBean(int is, String msg) {
		this.is = is;
		this.msg = msg;
	}

	public JsonBean(int is, Object data) {
		this.is = is;
		this.data = data;
	}

	public JsonBean(int is, String msg, Object data) {
		this.is = is;
		this.msg = msg;
		this.data = data;
	}

	public int getIs() {
		return this.is;
	}

	public void setIs(int is) {
		this.is = is;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}