package com.aten.model.orm;

import java.io.Serializable;
import java.math.BigInteger;

public class ReachAreaPa implements Serializable{
	private static final long serialVersionUID = 1513631504536027014L;
	private BigInteger rea_id;
	private BigInteger as_id;
	private String end_area;
	private String end_area_name;
	public ReachAreaPa() {
		super();
	}
	public ReachAreaPa(BigInteger rea_id, BigInteger as_id, String end_area, String end_area_name) {
		super();
		this.rea_id = rea_id;
		this.as_id = as_id;
		this.end_area = end_area;
		this.end_area_name = end_area_name;
	}
	public BigInteger getRea_id() {
		return rea_id;
	}
	public void setRea_id(BigInteger rea_id) {
		this.rea_id = rea_id;
	}
	public BigInteger getAs_id() {
		return as_id;
	}
	public void setAs_id(BigInteger as_id) {
		this.as_id = as_id;
	}
	public String getEnd_area() {
		return end_area;
	}
	public void setEnd_area(String end_area) {
		this.end_area = end_area;
	}
	public String getEnd_area_name() {
		return end_area_name;
	}
	public void setEnd_area_name(String end_area_name) {
		this.end_area_name = end_area_name;
	}
}
