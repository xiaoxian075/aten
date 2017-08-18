package com.aten.codebuild.bean;

public class TableField {
	//字段名称
	private String field_name;
	//字段的长芭
	private String field_length;
	//字段说明
	private String field_note;
	//是否必填
	private String is_must;
	//字段类型
	private String field_type;
	//提示信息
	private String tip_msg;
	//默认值 
	private String default_value;
	//验证数据格式类型
	private String data_type;
	//添加，修改页面显示的字段
	private String add_edit_field;
	//添加，修心页面的排序顺序
	private String add_edit_field_sort;
	//需在列表中显示的字段
	private String show_list_field;
	//列表中显示的顺序
	private String show_list_field_sort;
	//需要搜索的字段
	private String search_list_field;
	//搜索字段的顺序
	private String search_list_field_sort;
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	public String getField_length() {
		return field_length;
	}
	public void setField_length(String field_length) {
		this.field_length = field_length;
	}
	public String getField_note() {
		return field_note;
	}
	public void setField_note(String field_note) {
		this.field_note = field_note;
	}
	public String getIs_must() {
		return is_must;
	}
	public void setIs_must(String is_must) {
		this.is_must = is_must;
	}
	public String getField_type() {
		return field_type;
	}
	public void setField_type(String field_type) {
		this.field_type = field_type;
	}
	public String getDefault_value() {
		return default_value;
	}
	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}
	
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public String getAdd_edit_field() {
		return add_edit_field;
	}
	public void setAdd_edit_field(String add_edit_field) {
		this.add_edit_field = add_edit_field;
	}
	public String getAdd_edit_field_sort() {
		return add_edit_field_sort;
	}
	public void setAdd_edit_field_sort(String add_edit_field_sort) {
		this.add_edit_field_sort = add_edit_field_sort;
	}
	public String getShow_list_field() {
		return show_list_field;
	}
	public void setShow_list_field(String show_list_field) {
		this.show_list_field = show_list_field;
	}
	public String getShow_list_field_sort() {
		return show_list_field_sort;
	}
	public void setShow_list_field_sort(String show_list_field_sort) {
		this.show_list_field_sort = show_list_field_sort;
	}
	public String getSearch_list_field() {
		return search_list_field;
	}
	public void setSearch_list_field(String search_list_field) {
		this.search_list_field = search_list_field;
	}
	public String getSearch_list_field_sort() {
		return search_list_field_sort;
	}
	public void setSearch_list_field_sort(String search_list_field_sort) {
		this.search_list_field_sort = search_list_field_sort;
	}
	public String getTip_msg() {
		return tip_msg;
	}
	public void setTip_msg(String tip_msg) {
		this.tip_msg = tip_msg;
	}
	
	
	
	
}
