package com.aten.model.orm;

import java.util.List;

import com.aten.model.bean.StrStrBean;
import com.aten.model.bean.logiAreaList;

public class LogiInit {
	private List<logiAreaList> logiArea;
	private List<StrStrBean> sendtime;
	public LogiInit() {
		super();
	}
	public LogiInit(List<logiAreaList> logiArea, List<StrStrBean> sendtime) {
		super();
		this.logiArea = logiArea;
		this.sendtime = sendtime;
	}
	public List<logiAreaList> getLogiArea() {
		return logiArea;
	}
	public void setLogiArea(List<logiAreaList> logiArea) {
		this.logiArea = logiArea;
	}
	public List<StrStrBean> getSendtime() {
		return sendtime;
	}
	public void setSendtime(List<StrStrBean> sendtime) {
		this.sendtime = sendtime;
	}
}
