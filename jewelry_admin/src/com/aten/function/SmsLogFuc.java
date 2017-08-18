package com.aten.function;

import com.aten.model.orm.Smslog;
import com.aten.service.SmslogService;



public class SmsLogFuc extends SpringContextFuc{
	
	private static SmslogService smsLogService = (SmslogService) getContext().getBean("smsLogService");


	/**
	 * @author linjunqin
	 * @Description 短信发送记录接口
	 * @param
	 * @date 2017-2-16 下午2:23:35
	 */
	public static void sendMsgLog(String mobile,String msg,String type,String is_send){
		Smslog smslog = new Smslog();
		smslog.setSms_mobile(mobile);
		smslog.setSms_msg(msg);
		smslog.setSms_type(type);
		smslog.setIs_send(is_send);//发送状态
		smslog.setSms_code("0");//未用
		smslog.setResult_content("0");//未用
		smslog.setState("0");//未用
		smsLogService.insert(smslog);
	}
	
	public static void main(String[] args) {
	}
	
}
