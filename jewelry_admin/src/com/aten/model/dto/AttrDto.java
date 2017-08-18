package com.aten.model.dto;

import java.util.List;

public class AttrDto {

	private String attr_id;
	private String attr_type;
	private String quote_id;
	private String attr_name;
	private List<AttrValueDto> attrValueDtoList;
	
	public List<AttrValueDto> getAttrValueDtoList() {
		return attrValueDtoList;
	}
	public void setAttrValueDtoList(List<AttrValueDto> attrValueDtoList) {
		this.attrValueDtoList = attrValueDtoList;
	}
	public String getAttr_name() {
		return attr_name;
	}
	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}
	public String getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
	}
	public String getAttr_type() {
		return attr_type;
	}
	public void setAttr_type(String attr_type) {
		this.attr_type = attr_type;
	}
	public String getQuote_id() {
		return quote_id;
	}
	public void setQuote_id(String quote_id) {
		this.quote_id = quote_id;
	}
	
	
	
}
