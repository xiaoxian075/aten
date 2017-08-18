package com.aten.model.orm;

import java.io.Serializable;
import java.math.BigInteger;

public class OrderAuxiliary implements Serializable {
	private static final long serialVersionUID = -7483848188736551864L;
	
	private BigInteger oa_id;
	private String order_number;		//订单号
	private int invoice_open_type;		//发票开具类型	0:普通增值税发票 1：传用增值税发票
	private int invoice_type;			//发票类型  0:个人 1:企业
	private String invoice_header;		//发票抬头
	private String invoice_phone;		//发票电话号码
	private String invoice_email;		//发票邮箱
	private String invoice_tax_no;		//发票税务号
	private String invoice_unit_address;	//发票单位地址
	public OrderAuxiliary() {
		super();
	}
	public OrderAuxiliary(BigInteger oa_id, String order_number, int invoice_open_type, int invoice_type,
			String invoice_header, String invoice_phone, String invoice_email, String invoice_tax_no,
			String invoice_unit_address) {
		super();
		this.oa_id = oa_id;
		this.order_number = order_number;
		this.invoice_open_type = invoice_open_type;
		this.invoice_type = invoice_type;
		this.invoice_header = invoice_header;
		this.invoice_phone = invoice_phone;
		this.invoice_email = invoice_email;
		this.invoice_tax_no = invoice_tax_no;
		this.invoice_unit_address = invoice_unit_address;
	}
	public BigInteger getOa_id() {
		return oa_id;
	}
	public void setOa_id(BigInteger oa_id) {
		this.oa_id = oa_id;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public int getInvoice_open_type() {
		return invoice_open_type;
	}
	public void setInvoice_open_type(int invoice_open_type) {
		this.invoice_open_type = invoice_open_type;
	}
	public int getInvoice_type() {
		return invoice_type;
	}
	public void setInvoice_type(int invoice_type) {
		this.invoice_type = invoice_type;
	}
	public String getInvoice_header() {
		return invoice_header;
	}
	public void setInvoice_header(String invoice_header) {
		this.invoice_header = invoice_header;
	}
	public String getInvoice_phone() {
		return invoice_phone;
	}
	public void setInvoice_phone(String invoice_phone) {
		this.invoice_phone = invoice_phone;
	}
	public String getInvoice_email() {
		return invoice_email;
	}
	public void setInvoice_email(String invoice_email) {
		this.invoice_email = invoice_email;
	}
	public String getInvoice_tax_no() {
		return invoice_tax_no;
	}
	public void setInvoice_tax_no(String invoice_tax_no) {
		this.invoice_tax_no = invoice_tax_no;
	}
	public String getInvoice_unit_address() {
		return invoice_unit_address;
	}
	public void setInvoice_unit_address(String invoice_unit_address) {
		this.invoice_unit_address = invoice_unit_address;
	}
}
