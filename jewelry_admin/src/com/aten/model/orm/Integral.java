package com.aten.model.orm;

import java.io.Serializable;
import java.util.Date;

public class Integral implements Serializable {

	private static final long serialVersionUID = 1L;

	private String integral_id;
	private String integral_goods_name;
	private String integral_value;
	private String integral_goods_img;
	private String stock;
	private String sort_no;
	private String integral_detail;
	private String in_date;
	private String is_del;
	private String is_up;
	private String integral_number;

	public String getIntegral_id() {
		return integral_id;
	}

	public void setIntegral_id(String integral_id) {
		this.integral_id = integral_id;
	}

	public String getIntegral_goods_name() {
		return integral_goods_name;
	}

	public void setIntegral_goods_name(String integral_goods_name) {
		this.integral_goods_name = integral_goods_name;
	}

	public String getIntegral_value() {
		return integral_value;
	}

	public void setIntegral_value(String integral_value) {
		this.integral_value = integral_value;
	}

	public String getIntegral_goods_img() {
		return integral_goods_img;
	}

	public void setIntegral_goods_img(String integral_goods_img) {
		this.integral_goods_img = integral_goods_img;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getSort_no() {
		return sort_no;
	}

	public void setSort_no(String sort_no) {
		this.sort_no = sort_no;
	}

	public String getIntegral_detail() {
		return integral_detail;
	}

	public void setIntegral_detail(String integral_detail) {
		this.integral_detail = integral_detail;
	}

	public String getIn_date() {
		return in_date;
	}

	public void setIn_date(String in_date) {
		this.in_date = in_date;
	}

	public String getIs_del() {
		return is_del;
	}

	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}

	public String getIs_up() {
		return is_up;
	}

	public void setIs_up(String is_up) {
		this.is_up = is_up;
	}

	public String getIntegral_number() {
		return integral_number;
	}

	public void setIntegral_number(String integral_number) {
		this.integral_number = integral_number;
	}

}
