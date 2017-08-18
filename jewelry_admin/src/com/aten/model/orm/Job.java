package com.aten.model.orm;


import java.io.Serializable;
import java.util.Date;

public class Job implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String job_id;
	private String info_id;
	private String modoule;
	private String in_date;

	
	
	
	public String getJob_id() {
		return job_id;
	}
	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}
	
	public String getInfo_id() {
		return info_id;
	}
	public void setInfo_id(String info_id) {
		this.info_id = info_id;
	}
	
	public String getModoule() {
		return modoule;
	}
	public void setModoule(String modoule) {
		this.modoule = modoule;
	}
	
	public String getIn_date() {
		return in_date;
	}
	public void setIn_date(String in_date) {
		this.in_date = in_date;
	}
	
	
}

