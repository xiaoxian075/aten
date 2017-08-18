package com.communal.thirdservice.logistics;

import java.util.List;

public class LogisticsNode {
	private String mailNo;	//快递单号
	private String expSpellName;	//快递字母简称
	private String expTextName;	//快递公司名
	private String tel;	//快递公司电话
	private int status;	//-1 待查询 0 查询异常 1 暂无记录 2 在途中 3 派送中 4 已签收 5 用户拒签 6 疑难件 7 无效单 8 超时单 9 签收失败 10 退回
	private List<LogisticsChildNode> data;	//具体快递路径信息
	private String updateStr;	//数据最后更新的时间
	public LogisticsNode() {
		super();
	}
	public LogisticsNode(String mailNo, String updateStr, int status, String tel, String expSpellName,
			String expTextName, List<LogisticsChildNode> data) {
		super();
		this.mailNo = mailNo;
		this.updateStr = updateStr;
		this.status = status;
		this.tel = tel;
		this.expSpellName = expSpellName;
		this.expTextName = expTextName;
		this.data = data;
	}
	public String getMailNo() {
		return mailNo;
	}
	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}
	public String getUpdateStr() {
		return updateStr;
	}
	public void setUpdateStr(String updateStr) {
		this.updateStr = updateStr;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getExpSpellName() {
		return expSpellName;
	}
	public void setExpSpellName(String expSpellName) {
		this.expSpellName = expSpellName;
	}
	public String getExpTextName() {
		return expTextName;
	}
	public void setExpTextName(String expTextName) {
		this.expTextName = expTextName;
	}
	public List<LogisticsChildNode> getData() {
		return data;
	}
	public void setData(List<LogisticsChildNode> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "LogisticsNode [mailNo=" + mailNo + ", updateStr=" + updateStr + ", status=" + status + ", tel=" + tel
				+ ", expSpellName=" + expSpellName + ", expTextName=" + expTextName + ", data=" + data + "]";
	}
	
}
