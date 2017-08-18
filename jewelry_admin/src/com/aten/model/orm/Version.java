package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class Version implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String version_id;
	private String sys_type;
	private String update_version;
	private String update_apk_url;
	private String digital_version;
	private String is_update;
	private String update_logs;
	private String update_time;
	private String force_update;

	
	
	
	public String getVersion_id() {
		return version_id;
	}
	public void setVersion_id(String version_id) {
		this.version_id = version_id;
	}
	
	public String getSys_type() {
		return sys_type;
	}
	public void setSys_type(String sys_type) {
		this.sys_type = sys_type;
	}
	
	public String getUpdate_version() {
		return update_version;
	}
	public void setUpdate_version(String update_version) {
		this.update_version = update_version;
	}
	
	public String getUpdate_apk_url() {
		return update_apk_url;
	}
	public void setUpdate_apk_url(String update_apk_url) {
		this.update_apk_url = update_apk_url;
	}
	
	public String getDigital_version() {
		return digital_version;
	}
	public void setDigital_version(String digital_version) {
		this.digital_version = digital_version;
	}
	
	public String getIs_update() {
		return is_update;
	}
	public void setIs_update(String is_update) {
		this.is_update = is_update;
	}
	
	public String getUpdate_logs() {
		return update_logs;
	}
	public void setUpdate_logs(String update_logs) {
		this.update_logs = update_logs;
	}
	
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
	public String getForce_update() {
		return force_update;
	}
	public void setForce_update(String force_update) {
		this.force_update = force_update;
	}
	
	
}

