package com.aten.model.orm;


import com.communal.util.StringUtil;

import java.io.Serializable;
import java.util.Date;

public class Shake implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String shake_id;
	private String shake_name;
	private String start_time;
	private String end_time;
	private String activity_rule;
	private String everyone_draw_num;
	private String draw_num_day;
	private String lottery_activity_num;
	private String draw_out_time;
	private String state;
	private String  probability_winning;
	private String shakeState;


	public String getProbability_winning() {
		return probability_winning;
	}

	public void setProbability_winning(String probability_winning) {
		this.probability_winning = probability_winning;
	}

	public String getShakeState() {
		return shakeState;
	}

	public void setShakeState(String shakeState) {
		this.shakeState = shakeState;
	}

	public String getShake_id() {
		return shake_id;
	}
	public void setShake_id(String shake_id) {
		this.shake_id = shake_id;
	}
	
	public String getShake_name() {
		return shake_name;
	}
	public void setShake_name(String shake_name) {
		this.shake_name = shake_name;
	}
	
	public String getStart_time() {
		return StringUtil.getStandDate(start_time);
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	
	public String getEnd_time() {
		return StringUtil.getStandDate(end_time);
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	
	public String getActivity_rule() {
		return activity_rule;
	}
	public void setActivity_rule(String activity_rule) {
		this.activity_rule = activity_rule;
	}
	
	public String getEveryone_draw_num() {
		return everyone_draw_num;
	}
	public void setEveryone_draw_num(String everyone_draw_num) {
		this.everyone_draw_num = everyone_draw_num;
	}
	
	public String getDraw_num_day() {
		return draw_num_day;
	}
	public void setDraw_num_day(String draw_num_day) {
		this.draw_num_day = draw_num_day;
	}
	
	public String getLottery_activity_num() {
		return lottery_activity_num;
	}
	public void setLottery_activity_num(String lottery_activity_num) {
		this.lottery_activity_num = lottery_activity_num;
	}
	
	public String getDraw_out_time() {
		return draw_out_time;
	}
	public void setDraw_out_time(String draw_out_time) {
		this.draw_out_time = draw_out_time;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}

