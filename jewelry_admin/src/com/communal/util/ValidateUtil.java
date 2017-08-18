package com.communal.util;

import java.util.regex.Pattern;



/**
 * @author linjunqin
 * @Description 表单验证工具
 * @date 2016-12-13 上午11:52:22
 */
public class ValidateUtil {

	public ValidateUtil() {
	}

	// 数字
	public static Pattern PATTERN_DIGITAL = Pattern.compile("^\\d+$");
	// 移动电话
	public static Pattern PATTERN_MOBILE = Pattern
			.compile("^(\\+86)?0?(13|14|15|18)[0-9]{9}$");
	// 邮件地址
	public static Pattern PATTERN_EMAIL = Pattern
				.compile("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
	// 用户名由数字、字母、下划线组成
	public static Pattern PATTERN_USERNAME = Pattern.compile("^[A-Za-z0-9_]+$");


	
	/**
	 * @author linjunqin
	 * @Description 校验用户名
	 * @param
	 * @date 2016-12-13 下午12:02:24
	 */
	public static boolean isUserName(String userName) {
		if (userName == null) {
			return false;
		} else {
			return PATTERN_USERNAME.matcher(userName).matches();
		}
	}


	/**
	 * @author linjunqin
	 * @Description 校验手机
	 * @param
	 * @date 2016-12-13 下午12:03:00
	 */
	public static boolean isMobile(String mobile) {
		if (mobile == null)
			return false;
		else
			return PATTERN_MOBILE.matcher(mobile).matches();
	}
	
	/**
	 * @author linjunqin
	 * @Description 校验邮箱
	 * @param
	 * @date 2016-12-13 下午12:03:00
	 */
	public static boolean isEmail(String email) {
		if (email == null)
			return false;
		else
			return PATTERN_EMAIL.matcher(email).matches();
	}
	
	/**
	 * @author linjunqin
	 * @Description 校验数字
	 * @param
	 * @date 2017-2-8 上午11:07:00
	 */
	public static boolean isDigital(String Digital) {
		if (Digital == null)
			return false;
		else
			return PATTERN_DIGITAL.matcher(Digital).matches();
	}
	

	public static void main(String[] args) {

	}

}
