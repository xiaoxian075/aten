package com.aten.model.bean;

import java.io.Serializable;
import java.util.List;

import com.communal.thirdservice.logistics.LogisticsChildNode;

public class ExpressInfo implements Serializable {
	private static final long serialVersionUID = -2350054173214105568L;
	
	private String fast_name;
	private String fast_waybill;
	private List<LogisticsChildNode> arrLogistics;
	public ExpressInfo() {
		super();
	}
	public ExpressInfo(String fast_name, String fast_waybill, List<LogisticsChildNode> arrLogistics) {
		super();
		this.fast_name = fast_name;
		this.fast_waybill = fast_waybill;
		this.arrLogistics = arrLogistics;
	}
	public String getFast_name() {
		return fast_name;
	}
	public void setFast_name(String fast_name) {
		this.fast_name = fast_name;
	}
	public String getFast_waybill() {
		return fast_waybill;
	}
	public void setFast_waybill(String fast_waybill) {
		this.fast_waybill = fast_waybill;
	}
	public List<LogisticsChildNode> getArrLogistics() {
		return arrLogistics;
	}
	public void setArrLogistics(List<LogisticsChildNode> arrLogistics) {
		this.arrLogistics = arrLogistics;
	}

}
