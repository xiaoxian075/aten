package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class IncIndex implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String index_id;
	private String module;
	private String module_id;
	private String oper_method;
	private String oper_time;

	
	
	
	public String getIndex_id() {
		return index_id;
	}
	public void setIndex_id(String index_id) {
		this.index_id = index_id;
	}
	
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	
	public String getModule_id() {
		return module_id;
	}
	public void setModule_id(String module_id) {
		this.module_id = module_id;
	}
	
	public String getOper_method() {
		return oper_method;
	}
	public void setOper_method(String oper_method) {
		this.oper_method = oper_method;
	}
	
	public String getOper_time() {
		return oper_time;
	}
	public void setOper_time(String oper_time) {
		this.oper_time = oper_time;
	}
	
	
}

