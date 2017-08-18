/*
 * 系统名称:艾腾云返酒店平台
 * (C)  Copyright aten  2017-04-28 17:41:43  Corporation 
 * All rights reserved.
 * Package:com.aten.model.orm
 * FileName: Regional.java 
 * Author:linjunqin
 */
package com.aten.model.orm;


import com.aten.function.AreaFuc;
import com.communal.util.StringUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * @author linjunqin
 * @Function 区域交易管理  model
 * @date 2017-04-28 17:41:43
 */
public class Regional implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String reg_id;
	private String the_area;
	private String order_success_num;
	private String order_total_money;
	private String creation_date;
	private String sale_date;
	private String the_area_name;
	private String statistics_date;

	public String getStatistics_date() {
		return StringUtil.getStandDate(statistics_date);
	}

	public void setStatistics_date(String statistics_date) {
		this.statistics_date = statistics_date;
	}

	public String getThe_area_name() {
		return the_area_name;
	}

	public void setThe_area_name(String the_area_name) {
		this.the_area_name = AreaFuc.getAreaNameStr(the_area_name) ;
	}

	public String getSale_date() {
		return StringUtil.getStandDate(sale_date);
	}

	public void setSale_date(String sale_date) {
		this.sale_date = sale_date;
	}

	public String getReg_id() {
		return reg_id;
	}
	public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
	}

	public String getThe_area() {
		return the_area;
	}
	public void setThe_area(String the_area) {
		this.the_area = the_area;
	}
	
	public String getOrder_success_num() {
		return order_success_num;
	}
	public void setOrder_success_num(String order_success_num) {
		this.order_success_num = order_success_num;
	}
	
	public String getOrder_total_money() {
		return order_total_money;
	}
	public void setOrder_total_money(String order_total_money) {
		this.order_total_money = order_total_money;
	}
	public String getCreation_date() {
		return StringUtil.getStandDate(creation_date);
	}
	public void setCreation_date(String creation_date) {
		this.creation_date = creation_date;
	}
	
	

}

