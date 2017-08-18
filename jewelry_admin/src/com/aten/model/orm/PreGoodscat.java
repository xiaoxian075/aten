package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class PreGoodscat implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String pg_id;
	private String cat_id;
	private String precat_id;
	private String cat_name;


	public String getCat_name() {
		return cat_name;
	}

	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}

	public String getPg_id() {
		return pg_id;
	}
	public void setPg_id(String pg_id) {
		this.pg_id = pg_id;
	}
	
	public String getCat_id() {
		return cat_id;
	}
	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}
	
	public String getPrecat_id() {
		return precat_id;
	}
	public void setPrecat_id(String precat_id) {
		this.precat_id = precat_id;
	}
	
	
}

