package com.aten.model.orm;

import java.io.Serializable;
import java.math.BigInteger;

public class Account implements Serializable {
	private static final long serialVersionUID = 2845563945371382562L;
	
	private BigInteger id;
	private String create_time;		//创建时间
	private String login_name;
	private String pwd;
	private String nick_name;
	private String user_name;
	private int sex;
	private String mobile;
	private int lev;
	private BigInteger balance;
	private BigInteger integral;
	private BigInteger earnings;
	private String last_time;
	private int status;
	private String device_code;
	private int lon;
	private int lat;
	private String ip;
	private String token;
	private String birthday;
	private String head_pic;
	private String pay_pwd;
	private String format_earnings;
	private BigInteger total_earnings;
	private String push_code;

	public String getPay_pwd() {
		return pay_pwd;
	}

	public void setPay_pwd(String pay_pwd) {
		this.pay_pwd = pay_pwd;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getLev() {
		return lev;
	}

	public void setLev(int lev) {
		this.lev = lev;
	}

	public BigInteger getBalance() {
		return balance;
	}

	public void setBalance(BigInteger balance) {
		this.balance = balance;
	}

	public BigInteger getIntegral() {
		return integral;
	}

	public void setIntegral(BigInteger integral) {
		this.integral = integral;
	}

	public BigInteger getEarnings() {
		return earnings;
	}

	public void setEarnings(BigInteger earnings) {
		this.earnings = earnings;
	}

	public int getStatus() {
		return status;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getLast_time() {
		return last_time;
	}

	public void setLast_time(String last_time) {
		this.last_time = last_time;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDevice_code() {
		return device_code;
	}

	public void setDevice_code(String device_code) {
		this.device_code = device_code;
	}

	public int getLon() {
		return lon;
	}

	public void setLon(int lon) {
		this.lon = lon;
	}

	public int getLat() {
		return lat;
	}

	public void setLat(int lat) {
		this.lat = lat;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getHead_pic() {
		return head_pic;
	}

	public void setHead_pic(String head_pic) {
		this.head_pic = head_pic;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPush_code() {
		return push_code;
	}

	public void setPush_code(String push_code) {
		this.push_code = push_code;
	}

	public String getFormat_earnings() {
		return format_earnings;
	}

	public void setFormat_earnings(String format_earnings) {
		this.format_earnings = format_earnings;
	}

	public BigInteger getTotal_earnings() {
		return total_earnings;
	}

	public void setTotal_earnings(BigInteger total_earnings) {
		this.total_earnings = total_earnings;
	}
	
	

}
