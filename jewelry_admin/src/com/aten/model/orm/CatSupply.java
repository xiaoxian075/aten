package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class CatSupply implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String ident_id;
	private String cat_id;
	private String supply_id;
	private String supply_name;

	public String getSupply_name() {
		return supply_name;
	}

	public void setSupply_name(String supply_name) {
		this.supply_name = supply_name;
	}

	public String getIdent_id() {
		return ident_id;
	}
	public void setIdent_id(String ident_id) {
		this.ident_id = ident_id;
	}
	
	public String getCat_id() {
		return cat_id;
	}
	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}
	
	public String getSupply_id() {
		return supply_id;
	}
	public void setSupply_id(String supply_id) {
		this.supply_id = supply_id;
	}
	
	
}

