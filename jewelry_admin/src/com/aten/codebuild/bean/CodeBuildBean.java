package com.aten.codebuild.bean;

import java.util.List;

public class CodeBuildBean {
	
	//项目路径
	private String project_path;
	//需要生成的类名
	private String class_name;
	//模块名称
	private String model_name;
	//功能描述
	private String function_descri;
	//字段列表数据
	private List<TableField> tableFieldList;

	public String getProject_path() {
		return project_path;
	}

	public void setProject_path(String project_path) {
		this.project_path = project_path;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getModel_name() {
		return model_name;
	}

	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}

	public String getFunction_descri() {
		return function_descri;
	}

	public void setFunction_descri(String function_descri) {
		this.function_descri = function_descri;
	}

	public List<TableField> getTableFieldList() {
		return tableFieldList;
	}

	public void setTableFieldList(List<TableField> tableFieldList) {
		this.tableFieldList = tableFieldList;
	}
	
	
	
}
