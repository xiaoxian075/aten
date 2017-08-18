package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class FullIndex implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String full_index_id;
	private String module;
	private String oper_time;
	private String index_version;
	private String oper_man;
	private String use_version;

	
	
	
	public String getFull_index_id() {
		return full_index_id;
	}
	public void setFull_index_id(String full_index_id) {
		this.full_index_id = full_index_id;
	}
	
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	
	public String getOper_time() {
		return oper_time;
	}
	public void setOper_time(String oper_time) {
		this.oper_time = oper_time;
	}
	
	public String getIndex_version() {
		return index_version;
	}
	public void setIndex_version(String index_version) {
		this.index_version = index_version;
	}
	
	public String getOper_man() {
		return oper_man;
	}
	public void setOper_man(String oper_man) {
		this.oper_man = oper_man;
	}
	
	public String getUse_version() {
		return use_version;
	}
	public void setUse_version(String use_version) {
		this.use_version = use_version;
	}
	
	
}

