package com.aten.model.bean;

/**
 * @author linjunqin
 * @Description 分页组件
 * @param
 * @date 2017-1-18 上午9:00:13
 */
public class PageListBean {
	
	private int current_s ;//当前页
	private int pagesize_s;//当前页面显示的条数
	private int pageStart;//当前开始的显示页
	private int pageTop;//当前结束的显示页
	private int totalCount;//共多少条记录
	private int totalPage; // 共多少页
	private int maxCurrentPage;
	private int minCurrentPage;
	private String current_name = "current_s";//当前页的名称

	public String getCurrent_name() {
		return current_name;
	}
	public void setCurrent_name(String current_name) {
		this.current_name = current_name;
	}
	public int getPagesize_s() {
		return pagesize_s;
	}
	public void setPagesize_s(int pagesize_s) {
		this.pagesize_s = pagesize_s;
	}
	public int getCurrent_s() {
		return current_s;
	}
	public void setCurrent_s(int current_s) {
		this.current_s = current_s;
	}
	//开始位置记录
	public int getPageStart() {
		if(current_s > totalPage){
			current_s=1;
			pageStart= 1;
		}else{
			pageStart = (current_s-1) * pagesize_s+1;
		}
		return pageStart;
	}
	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}
	//结束位置的记录
	public int getPageTop() {
		pageTop = current_s*pagesize_s;
		if(pageTop>totalCount){
			pageTop = totalCount;
		}
		return pageTop;
	}
	public void setPageTop(int pageTop) {
		this.pageTop = pageTop;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(int totalCount) {
		//总条数
		this.totalCount = totalCount;
		//总页数
		if (totalCount==0) {
			totalPage = 0;
		} else if(totalCount % pagesize_s ==0){
			totalPage = totalCount / pagesize_s;
		}else{
			totalPage = totalCount / pagesize_s + 1;
		}
		if (totalPage==0) totalPage = 1 ;
		//设置当前页的往后几页最大值
		maxCurrentPage = current_s+3;
		if(maxCurrentPage>totalPage) maxCurrentPage=totalPage;
		//设置当前页的往前几页最小值
		minCurrentPage = current_s-3;
		if(minCurrentPage<1) minCurrentPage=1;	
		
		
	}
	
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public int getMaxCurrentPage() {
		return maxCurrentPage;
	}
	public void setMaxCurrentPage(int maxCurrentPage) {
		this.maxCurrentPage = maxCurrentPage;
	}
	
	public int getMinCurrentPage() {
		return minCurrentPage;
	}
	public void setMinCurrentPage(int minCurrentPage) {
		this.minCurrentPage = minCurrentPage;
	}
	
	
}
