package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class Annex implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String annex_id;
	private String back_id;
	private String the_cat;
	private String back_type;
	private String info_id;
	private String annex_type;
	private String up_file_name;
	private String annex_url;
	private String remark;
	private String in_date;
	private String sort_no;
	private String is_del;
	private String file_size;
	
	
	public String getAnnex_id() {
		return annex_id;
	}
	public void setAnnex_id(String annex_id) {
		this.annex_id = annex_id;
	}
	
	public String getBack_id() {
		return back_id;
	}
	public void setBack_id(String back_id) {
		this.back_id = back_id;
	}
	
	public String getThe_cat() {
		return the_cat;
	}
	public void setThe_cat(String the_cat) {
		this.the_cat = the_cat;
	}
	
	public String getBack_type() {
		return back_type;
	}
	public void setBack_type(String back_type) {
		this.back_type = back_type;
	}
	
	public String getInfo_id() {
		return info_id;
	}
	public void setInfo_id(String info_id) {
		this.info_id = info_id;
	}
	
	public String getAnnex_type() {
		return annex_type;
	}
	public void setAnnex_type(String annex_type) {
		this.annex_type = annex_type;
	}
	
	public String getUp_file_name() {
		return up_file_name;
	}
	public void setUp_file_name(String up_file_name) {
		this.up_file_name = up_file_name;
	}
	
	public String getAnnex_url() {
		return annex_url;
	}
	public void setAnnex_url(String annex_url) {
		this.annex_url = annex_url;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getIn_date() {
		return in_date;
	}
	public void setIn_date(String in_date) {
		this.in_date = in_date;
	}
	
	public String getSort_no() {
		return sort_no;
	}
	public void setSort_no(String sort_no) {
		this.sort_no = sort_no;
	}
	
	public String getIs_del() {
		return is_del;
	}
	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}
	
	public String getFile_size() {
		return file_size;
	}
	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}
	
	
}

