package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class ShakeWinningRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String wr_id;
	private String shake_id;
	private String awards_id;
	private String login_name;
	private String account_id;
	private String draw_time;
	private String is_draw;
	private String accept_time;
	private String shake_name;
	private String awards_name;


	public String getShake_name() {
		return shake_name;
	}

	public void setShake_name(String shake_name) {
		this.shake_name = shake_name;
	}

	public String getAwards_name() {
		return awards_name;
	}

	public void setAwards_name(String awards_name) {
		this.awards_name = awards_name;
	}

	public String getWr_id() {
		return wr_id;
	}
	public void setWr_id(String wr_id) {
		this.wr_id = wr_id;
	}
	
	public String getShake_id() {
		return shake_id;
	}
	public void setShake_id(String shake_id) {
		this.shake_id = shake_id;
	}
	
	public String getAwards_id() {
		return awards_id;
	}
	public void setAwards_id(String awards_id) {
		this.awards_id = awards_id;
	}
	
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	
	public String getDraw_time() {
		return draw_time;
	}
	public void setDraw_time(String draw_time) {
		this.draw_time = draw_time;
	}
	
	public String getIs_draw() {
		return is_draw;
	}
	public void setIs_draw(String is_draw) {
		this.is_draw = is_draw;
	}
	
	public String getAccept_time() {
		return accept_time;
	}
	public void setAccept_time(String accept_time) {
		this.accept_time = accept_time;
	}
	
	
}

