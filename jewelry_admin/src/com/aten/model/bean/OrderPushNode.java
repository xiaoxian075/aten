package com.aten.model.bean;

import java.io.Serializable;

public class OrderPushNode implements Serializable {
	private static final long serialVersionUID = 6766968811987275488L;
	
	private String mobile;
	private String pushCode;
	private String title;
	private String content;
	private String orderId;
	private String type;
	private String opr;
	
	public OrderPushNode() {
		super();
	}
	public OrderPushNode(String mobile, String pushCode, String title, String content, String orderId, String type,
			String opr) {
		super();
		this.mobile = mobile;
		this.pushCode = pushCode;
		this.title = title;
		this.content = content;
		this.orderId = orderId;
		this.type = type;
		this.opr = opr;
	}
	//	if (account!=null) {
//		SmsClient.send(account.getMobile(), content);
//	}
//	
//	//系统推送
//	if (account.getPush_code()!=null) {
//		JiguangClient.send(new JPushBean(account.getPush_code(),title,content,refund.getRefund_id(),"0",opr));
//	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPushCode() {
		return pushCode;
	}
	public void setPushCode(String pushCode) {
		this.pushCode = pushCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOpr() {
		return opr;
	}
	public void setOpr(String opr) {
		this.opr = opr;
	}
}
