package com.communal.thirdservice.logistics;

public class LogisticsChildNode {
	private String context;	//内容
	private String time;	//时间
	public LogisticsChildNode() {
		super();
	}
	public LogisticsChildNode(String context, String time) {
		super();
		this.context = context;
		this.time = time;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "LogisticsChildNode [context=" + context + ", time=" + time + "]";
	}
}
