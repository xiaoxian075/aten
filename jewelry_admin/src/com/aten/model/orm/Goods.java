package com.aten.model.orm;

import java.io.Serializable;
import java.util.Date;

import com.communal.util.AmountUtil;
import com.communal.util.StringUtil;

public class Goods implements Serializable{

	private static final long serialVersionUID = 1L;
	private String goods_id;
	private String com_id;
	private String cat_id;
	private String goods_name;
	private String brand_id;
	private String supply_id;
	private String appraisal_id;
	private String sale_mode;
	private String presale_model;
	private String list_img;
	private String show_imgs;
	private String fixed_price;
	private String price;
	private String lower_price;
	private String height_price;
	private String weight;
	private String volume;
	private String goods_detail;
	private String ship_template;
	private String state;
	private String is_del;
	private String in_date;
	private String mana_id;
	private String customer_service;
	private String stock_type;
	private String total_stock;
	private String total_sales;
	private String info_state;
	private String audit_state;
	private String divide_rate;
	private String add_time;
	private String cat_name;
	private String catName;
	private String is_off_the_shelf;
	private String manual_fee;
	private String goods_number;

	public String getGoods_number() {
		return goods_number;
	}

	public void setGoods_number(String goods_number) {
		this.goods_number = goods_number;
	}

	public String getManual_fee() {
		return manual_fee;
	}

	public void setManual_fee(String manual_fee) {
		this.manual_fee = manual_fee;
	}

	public String getIs_off_the_shelf() {
		return is_off_the_shelf;
	}

	public void setIs_off_the_shelf(String is_off_the_shelf) {
		this.is_off_the_shelf = is_off_the_shelf;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getCat_name() {
		return cat_name;
	}
	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String add_time) {
		this.add_time = StringUtil.getStandDate(add_time);
	}
	public String getLower_price() {
		return lower_price;
	}
	public void setLower_price(String lower_price) {
		this.lower_price = lower_price;
	}
	public String getHeight_price() {
		return height_price;
	}
	public void setHeight_price(String height_price) {
		this.height_price = height_price;
	}
	public String getDivide_rate() {
		return divide_rate;
	}
	public void setDivide_rate(String divide_rate) {
		this.divide_rate = divide_rate;
	}
	public String getCom_id() {
		return com_id;
	}
	public void setCom_id(String com_id) {
		this.com_id = com_id;
	}
	public String getInfo_state() {
		return info_state;
	}
	public void setInfo_state(String info_state) {
		this.info_state = info_state;
	}
	public String getAudit_state() {
		return audit_state;
	}
	public void setAudit_state(String audit_state) {
		this.audit_state = audit_state;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getCat_id() {
		return cat_id;
	}
	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}
	public String getSupply_id() {
		return supply_id;
	}
	public void setSupply_id(String supply_id) {
		this.supply_id = supply_id;
	}
	public String getAppraisal_id() {
		return appraisal_id;
	}
	public void setAppraisal_id(String appraisal_id) {
		this.appraisal_id = appraisal_id;
	}
	public String getSale_mode() {
		return sale_mode;
	}
	public void setSale_mode(String sale_mode) {
		this.sale_mode = sale_mode;
	}
	public String getPresale_model() {
		return presale_model;
	}
	public void setPresale_model(String presale_model) {
		this.presale_model = presale_model;
	}
	public String getList_img() {
		return list_img;
	}
	public void setList_img(String list_img) {
		this.list_img = list_img;
	}
	public String getShow_imgs() {
		return show_imgs;
	}
	public void setShow_imgs(String show_imgs) {
		this.show_imgs = show_imgs;
	}
	public String getFixed_price() {
		return fixed_price;
	}
	public void setFixed_price(String fixed_price) {
		this.fixed_price = fixed_price;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getGoods_detail() {
		return goods_detail;
	}
	public void setGoods_detail(String goods_detail) {
		this.goods_detail = goods_detail;
	}
	public String getShip_template() {
		return ship_template;
	}
	public void setShip_template(String ship_template) {
		this.ship_template = ship_template;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIs_del() {
		return is_del;
	}
	public void setIs_del(String is_del) {
		this.is_del = is_del;
	}
	public String getIn_date() {
		return in_date;
	}
	public void setIn_date(String in_date) {
		this.in_date = StringUtil.getStandDate(in_date);
	}
	public String getMana_id() {
		return mana_id;
	}
	public void setMana_id(String mana_id) {
		this.mana_id = mana_id;
	}
	public String getCustomer_service() {
		return customer_service;
	}
	public void setCustomer_service(String customer_service) {
		this.customer_service = customer_service;
	}
	public String getStock_type() {
		return stock_type;
	}
	public void setStock_type(String stock_type) {
		this.stock_type = stock_type;
	}
	public String getTotal_stock() {
		return total_stock;
	}
	public void setTotal_stock(String total_stock) {
		this.total_stock = total_stock;
	}
	public String getTotal_sales() {
		return total_sales;
	}
	public void setTotal_sales(String total_sales) {
		this.total_sales = total_sales;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
