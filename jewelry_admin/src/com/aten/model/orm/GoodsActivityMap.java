package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class GoodsActivityMap implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String gam_id;
	private String goods_id;
	private String activity_id;
	private String goods_name;
	private String fixed_price;
	private String price;
	private String activity_price;

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getFixed_price() {
		return fixed_price;
	}

	public void setFixed_price(String fixed_price) {
		this.fixed_price = fixed_price;
	}

	public String getActivity_price() {
		return activity_price;
	}

	public void setActivity_price(String activity_price) {
		this.activity_price = activity_price;
	}

	public String getGam_id() {
		return gam_id;
	}
	public void setGam_id(String gam_id) {
		this.gam_id = gam_id;
	}
	
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	
	public String getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}

}

