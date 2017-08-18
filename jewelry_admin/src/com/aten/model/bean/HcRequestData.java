package com.aten.model.bean;

/**
 * @author linjunqin
 * @Description HttpClient 请求的封装数据
 * @param
 * @date 2017-2-9 上午9:55:03
 */
public class HcRequestData {
	
	//返回的状态码
	private int stateCode;
	//返回的结果
	private String result;
	
	
	public int getStateCode() {
		return stateCode;
	}
	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
}
