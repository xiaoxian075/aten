package com.aten.model.bean;

import java.util.List;

import com.aten.model.orm.GoodsCustomAttr;

public class CustomAttrListBean {

	private List<GoodsCustomAttr>  customAttrList;

	public List<GoodsCustomAttr> getCustomAttrList() {
		return customAttrList;
	}

	public void setCustomAttrList(List<GoodsCustomAttr> customAttrList) {
		this.customAttrList = customAttrList;
	}

}
