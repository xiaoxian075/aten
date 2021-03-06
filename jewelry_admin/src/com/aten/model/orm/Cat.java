package com.aten.model.orm;


import java.util.List;

/**
 * @author linjunqin
 * @Description 系统分类管理
 * @date 2016-12-13  Tue Jan 03 16:51:49 CST 2017
 */
public class Cat  {

	private String cat_id;
	private String cat_name;
	private String cat_img;
	private String en_name;
	private String word_index;
	private String parent_cat_id;
	private String cat_level;
	private String sort_no;
	private String level_cat;
	private String is_sys;
	private String state;
	private String note;
	private String sel_cat_img;
	private String module;
	private String parent_name;
	private String[] brandIds;
	private String[] supplyIds;
	private String[] catIds;
	private String[] appraisalIds;
	private String  divide_rate;
	private String manual_fee;
	private List<CatAttr> catSkuAttrList;
	private List<CatAttr> catKeyAttrList;
	private String parent_level_name;

	public String getManual_fee() {
		return manual_fee;
	}

	public void setManual_fee(String manual_fee) {
		this.manual_fee = manual_fee;
	}

	public String[] getCatIds() {
		return catIds;
	}

	public void setCatIds(String[] catIds) {
		this.catIds = catIds;
	}

	public String getParent_name() {
		return parent_name;
	}

	public String getParent_level_name() {
		return parent_level_name;
	}

	public void setParent_level_name(String parent_level_name) {
		this.parent_level_name = parent_level_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	public List<CatAttr> getCatSkuAttrList() {
		return catSkuAttrList;
	}

	public void setCatSkuAttrList(List<CatAttr> catSkuAttrList) {
		this.catSkuAttrList = catSkuAttrList;
	}

	public List<CatAttr> getCatKeyAttrList() {
		return catKeyAttrList;
	}

	public void setCatKeyAttrList(List<CatAttr> catKeyAttrList) {
		this.catKeyAttrList = catKeyAttrList;
	}

	public String getDivide_rate() {
		 return divide_rate;
	 }

	 public void setDivide_rate(String divide_rate) {
		 this.divide_rate = divide_rate;
	 }

	 public String[] getBrandIds() {
		 return brandIds;
	 }

	 public void setBrandIds(String[] brandIds) {
		 this.brandIds = brandIds;
	 }

	 public String[] getSupplyIds() {
		 return supplyIds;
	 }

	 public void setSupplyIds(String[] supplyIds) {
		 this.supplyIds = supplyIds;
	 }

	 public String[] getAppraisalIds() {
		 return appraisalIds;
	 }

	 public void setAppraisalIds(String[] appraisalIds) {
		 this.appraisalIds = appraisalIds;
	 }

	 public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getSel_cat_img() {
		return sel_cat_img;
	}
	public void setSel_cat_img(String sel_cat_img) {
		this.sel_cat_img = sel_cat_img;
	}
	public String getCat_id() {
		return cat_id;
	}
	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}
	
	public String getCat_name() {
		return cat_name;
	}
	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}
	public String getCat_img() {
		return cat_img;
	}
	public void setCat_img(String cat_img) {
		this.cat_img = cat_img;
	}
	public String getEn_name() {
		return en_name;
	}
	public void setEn_name(String en_name) {
		this.en_name = en_name;
	}
	
	public String getWord_index() {
		return word_index;
	}
	public void setWord_index(String word_index) {
		this.word_index = word_index;
	}
	
	public String getParent_cat_id() {
		return parent_cat_id;
	}
	public void setParent_cat_id(String parent_cat_id) {
		this.parent_cat_id = parent_cat_id;
	}
	
	public String getCat_level() {
		return cat_level;
	}
	public void setCat_level(String cat_level) {
		this.cat_level = cat_level;
	}
	
		
	public String getSort_no() {
		return sort_no;
	}
	public void setSort_no(String sort_no) {
		this.sort_no = sort_no;
	}
	
	public String getLevel_cat() {
		return level_cat;
	}
	public void setLevel_cat(String level_cat) {
		this.level_cat = level_cat;
	}
	
	public String getIs_sys() {
		return is_sys;
	}
	public void setIs_sys(String is_sys) {
		this.is_sys = is_sys;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
}

