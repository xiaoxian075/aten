package com.aten.model.bean;

import java.util.List;

import com.aten.model.AreaShort;

public class logiAreaList {
	private String area_id;
	private String area_name;
	private List<AreaShort> child_area;
	private String region;
	public logiAreaList() {
		super();
	}
	public logiAreaList(String area_id, String area_name, List<AreaShort> child_area, String region) {
		super();
		this.area_id = area_id;
		this.area_name = area_name;
		this.child_area = child_area;
		this.region = region;
	}
	public String getArea_id() {
		return area_id;
	}
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public List<AreaShort> getChild_area() {
		return child_area;
	}
	public void setChild_area(List<AreaShort> child_area) {
		this.child_area = child_area;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
}
