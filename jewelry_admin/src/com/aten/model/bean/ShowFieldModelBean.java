package com.aten.model.bean;

import java.util.List;

public class ShowFieldModelBean {

	//需要显示字段的字符串
	private String showFields;
	
	//获取字段列表数组
	private List<ShowFieldBean> showFieldList;

	public String getShowFields() {
		return showFields;
	}

	public void setShowFields(String showFields) {
		this.showFields = showFields;
	}

	public List<ShowFieldBean> getShowFieldList() {
		return showFieldList;
	}

	public void setShowFieldList(List<ShowFieldBean> showFieldList) {
		this.showFieldList = showFieldList;
	}
	
}
