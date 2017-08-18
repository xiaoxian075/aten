package com.aten.model.orm;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

public class OrderExpressNode implements Serializable{
	private static final long serialVersionUID = -8369531591421754858L;
	
	private BigInteger oe_id;
	private String order_number;		//订单号
	private String consignee;			//收货人
	private String consignee_area;		//收件人地区
	private String consignee_address;	//收件人地址
	private String consignee_postcode;	//收件人编码
	private String consignee_mobile;	//收件人手机号
	private String fast_code;			//快递公司代码
	private String fast_name;			//快递公司名称
	private String fast_waybill;		//快递单号
	private String logistics_record;	//具体快递路径信息
	private String fast_phone;			//快递电话
	private int logistics_state;		//-1 待查询 0 查询异常 1 暂无记录 2 在途中 3 派送中 4 已签收 5 用户拒签 6 疑难件 7 无效单 8 超时单 9 签收失败 10 退回
	private Timestamp last_update_time;	//最后时间更新时间
	public BigInteger getOe_id() {
		return oe_id;
	}
	public void setOe_id(BigInteger oe_id) {
		this.oe_id = oe_id;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getConsignee_area() {
		return consignee_area;
	}
	public void setConsignee_area(String consignee_area) {
		this.consignee_area = consignee_area;
	}
	public String getConsignee_address() {
		return consignee_address;
	}
	public void setConsignee_address(String consignee_address) {
		this.consignee_address = consignee_address;
	}
	public String getConsignee_postcode() {
		return consignee_postcode;
	}
	public void setConsignee_postcode(String consignee_postcode) {
		this.consignee_postcode = consignee_postcode;
	}
	public String getConsignee_mobile() {
		return consignee_mobile;
	}
	public void setConsignee_mobile(String consignee_mobile) {
		this.consignee_mobile = consignee_mobile;
	}
	public String getFast_code() {
		return fast_code;
	}
	public void setFast_code(String fast_code) {
		this.fast_code = fast_code;
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
	public String getLogistics_record() {
		return logistics_record;
	}
	public void setLogistics_record(String logistics_record) {
		this.logistics_record = logistics_record;
	}
	public int getLogistics_state() {
		return logistics_state;
	}
	public void setLogistics_state(int logistics_state) {
		this.logistics_state = logistics_state;
	}
	public Timestamp getLast_update_time() {
		return last_update_time;
	}
	public void setLast_update_time(Timestamp last_update_time) {
		this.last_update_time = last_update_time;
	}
	public String getFast_phone() {
		return fast_phone;
	}
	public void setFast_phone(String fast_phone) {
		this.fast_phone = fast_phone;
	}
}
