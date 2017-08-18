package com.aten.model.bean;

public class MongoBean {
	
	//搜索的字段名称
	private String name;
	//搜索的字段值
	private String value;
	//搜索类型
	private String type; //1:模糊搜索
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
