package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class ShakeAwards implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String awards_id;
	private String shake_id;
	private String awards_name;
	private String prize_type;
	private String give_integral;
	private String coupon_id;
	private String awards_level;
	private String awards_level_name;
	private String awards_probability;
	private String awards_num;
	private String shake_name;
	private int get_num;

	public String getShake_name() {
		return shake_name;
	}

	public void setShake_name(String shake_name) {
		this.shake_name = shake_name;
	}

	public String getAwards_id() {
		return awards_id;
	}
	public void setAwards_id(String awards_id) {
		this.awards_id = awards_id;
	}
	
	public String getShake_id() {
		return shake_id;
	}
	public void setShake_id(String shake_id) {
		this.shake_id = shake_id;
	}
	
	public String getAwards_name() {
		return awards_name;
	}
	public void setAwards_name(String awards_name) {
		this.awards_name = awards_name;
	}
	
	public String getPrize_type() {
		return prize_type;
	}
	public void setPrize_type(String prize_type) {
		this.prize_type = prize_type;
	}
	
	public String getGive_integral() {
		return give_integral;
	}
	public void setGive_integral(String give_integral) {
		this.give_integral = give_integral;
	}
	
	public String getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}
	
	public String getAwards_level() {
		return awards_level;
	}
	public void setAwards_level(String awards_level) {
		this.awards_level = awards_level;
	}
	
	public String getAwards_level_name() {
		return awards_level_name;
	}
	public void setAwards_level_name(String awards_level_name) {
		this.awards_level_name = awards_level_name;
	}
	
	public String getAwards_probability() {
		return awards_probability;
	}
	public void setAwards_probability(String awards_probability) {
		this.awards_probability = awards_probability;
	}
	
	public String getAwards_num() {
		return awards_num;
	}
	public void setAwards_num(String awards_num) {
		this.awards_num = awards_num;
	}

	public int getGet_num() {
		return get_num;
	}

	public void setGet_num(int get_num) {
		this.get_num = get_num;
	}
}

