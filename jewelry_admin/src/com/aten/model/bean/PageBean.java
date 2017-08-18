package com.aten.model.bean;

import java.util.List;

public class PageBean<T> {
	private PageListBean page;
	private List<T> arrList;
	public PageBean(PageListBean page, List<T> arrList) {
		super();
		this.page = page;
		this.arrList = arrList;
	}
	public PageListBean getPage() {
		return page;
	}
	public void setPage(PageListBean page) {
		this.page = page;
	}
	public List<T> getArrList() {
		return arrList;
	}
	public void setArrList(List<T> arrList) {
		this.arrList = arrList;
	}
}
