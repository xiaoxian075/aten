package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

 /**
 * @author linjunqin
 * @Description 角色
 * @date 2016-12-13  2017-01-05 16:47:51
 */
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String role_code;
	private String role_name;
	private String note;
	private String add_time;
	private String menu_right;
	private String power_right;
	private String plat_role;
	private String is_sys;
	private String state;
	
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPlat_role() {
		return plat_role;
	}
	public void setPlat_role(String plat_role) {
		this.plat_role = plat_role;
	}
	public String getIs_sys() {
		return is_sys;
	}
	public void setIs_sys(String is_sys) {
		this.is_sys = is_sys;
	}
	public String getMenu_right() {
		return menu_right;
	}
	public void setMenu_right(String menu_right) {
		this.menu_right = menu_right;
	}
	public String getPower_right() {
		return power_right;
	}
	public void setPower_right(String power_right) {
		this.power_right = power_right;
	}
	public String getRole_code() {
		return role_code;
	}
	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}
	
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String add_time) {
		if(add_time.indexOf(".0")>-1)
			add_time = add_time.replace(".0", "");
		this.add_time = add_time;
	}
	
	
}

