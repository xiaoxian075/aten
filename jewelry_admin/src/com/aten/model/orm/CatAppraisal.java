package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class CatAppraisal implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String ident_id;
	private String appraisal_id;
	private String cat_id;
	private String appraisal_name;

	public String getAppraisal_name() {
		return appraisal_name;
	}

	public void setAppraisal_name(String appraisal_name) {
		this.appraisal_name = appraisal_name;
	}

	public String getIdent_id() {
		return ident_id;
	}
	public void setIdent_id(String ident_id) {
		this.ident_id = ident_id;
	}
	
	public String getAppraisal_id() {
		return appraisal_id;
	}
	public void setAppraisal_id(String appraisal_id) {
		this.appraisal_id = appraisal_id;
	}
	
	public String getCat_id() {
		return cat_id;
	}
	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}
	
	
}

