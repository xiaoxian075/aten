package com.aten.model.bean;

public class ValidateBean {
	
	//验证的字段名称
	private String name;
	//字段类型
	private String type;
	//是否必填
	private String required;
	//字段最大长度
	private String maxlen;
	//字段最小长度
	private String minlen;
	//提示信息
	private String tip;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getMaxlen() {
		return maxlen;
	}
	public void setMaxlen(String maxlen) {
		this.maxlen = maxlen;
	}
	public String getMinlen() {
		return minlen;
	}
	public void setMinlen(String minlen) {
		this.minlen = minlen;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
	}
}
