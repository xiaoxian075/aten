package com.aten.model.orm;

import java.io.Serializable;

public class News implements Serializable {

	private static final long serialVersionUID = 1L;

	private String news_id;
	private String news_title;
	private String introduction;
	private String the_cat;
	private String news_picture;
	private String news_author;
	private String news_detail;
	private String is_top;
	private String state;
	private String issue_time;
	private String create_time;
	private String sort_no;

	public String getNews_id() {
		return news_id;
	}

	public void setNews_id(String news_id) {
		this.news_id = news_id;
	}

	public String getNews_title() {
		return news_title;
	}

	public void setNews_title(String news_title) {
		this.news_title = news_title;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getThe_cat() {
		return the_cat;
	}

	public void setThe_cat(String the_cat) {
		this.the_cat = the_cat;
	}

	public String getNews_picture() {
		return news_picture;
	}

	public void setNews_picture(String news_picture) {
		this.news_picture = news_picture;
	}

	public String getNews_author() {
		return news_author;
	}

	public void setNews_author(String news_author) {
		this.news_author = news_author;
	}

	public String getNews_detail() {
		return news_detail;
	}

	public void setNews_detail(String news_detail) {
		this.news_detail = news_detail;
	}

	public String getIs_top() {
		return is_top;
	}

	public void setIs_top(String is_top) {
		this.is_top = is_top;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIssue_time() {
		return issue_time;
	}

	public void setIssue_time(String issue_time) {
		this.issue_time = issue_time;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getSort_no() {
		return sort_no;
	}

	public void setSort_no(String sort_no) {
		this.sort_no = sort_no;
	}

}
