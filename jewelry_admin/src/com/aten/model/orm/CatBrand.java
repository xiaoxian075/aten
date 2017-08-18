package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class CatBrand implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String ident_id;
	private String brand_id;
	private String cat_id;
	private String brand_name;

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getIdent_id() {
		return ident_id;
	}
	public void setIdent_id(String ident_id) {
		this.ident_id = ident_id;
	}
	
	public String getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}
	
	public String getCat_id() {
		return cat_id;
	}
	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}
	
	
}

