package com.aten.client;

import java.io.UnsupportedEncodingException;
import com.alibaba.fastjson.JSON;
import com.aten.client.clsms.request.SmsSendRequest;
import com.aten.client.clsms.util.ChuangLanSmsUtil;
import com.aten.function.SysconfigFuc;


public class SmsClient {

	
	// 用户平台API账号(非登录账号,示例:N1234567)
	public static String smsSingleRequestServerUrl = SysconfigFuc.getSysValue("cfg_sms_url");
	// 用户平台API账号(非登录账号,示例:N1234567)
	public static String account = SysconfigFuc.getSysValue("cfg_sms_account");
	// 用户平台API密码(非登录密码)
	public static String pswd = SysconfigFuc.getSysValue("cfg_sms_password");

	public static void main(String[] args) throws UnsupportedEncodingException {

		send("15959371663","test");
	}

	/**
	 * @author linjunqin
	 * @Description  发送短信
	 * @param
	 * @date 2017年7月25日 下午9:33:04 
	 */
	public static void send(String phone,String msg){
		String report= "true";
		SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phone,report);
		String requestJson = JSON.toJSONString(smsSingleRequest);
		ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);
		System.out.println("发送成功！");
	}
	

}
