package com.aten.model.bean;

/**
 * @author linjunqin
 * @Description 树表对应信息对象
 * @param
 * @date 2017-1-18 上午9:00:49
 */
public class TreeTableBean {

	private String id;
	private String up_id;
	private String back_sel_id;
	public String getUp_id() {
		return up_id;
	}
	public void setUp_id(String up_id) {
		this.up_id = up_id;
	}
	public String getBack_sel_id() {
		return back_sel_id;
	}
	public void setBack_sel_id(String back_sel_id) {
		this.back_sel_id = back_sel_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
