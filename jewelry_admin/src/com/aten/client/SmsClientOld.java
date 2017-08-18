package com.aten.client;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import com.aten.function.SysconfigFuc;

/**
 * @author Beyond
 */
public class SmsClientOld {
	
	//短信发送接口地址
	public static String SMS_URL = "http://smssh1.253.com/msg/send/json";//SysconfigFuc.getSysValue("cfg_sms_url");
	//短信发送的帐户名
	public static String SMS_ACCOUNT ="N5364332";//SysconfigFuc.getSysValue("cfg_sms_account");
	//短信发送的密码
	public static String SMS_PASSWORD = "vqOB2zgx5J3d08";//SysconfigFuc.getSysValue("cfg_sms_password");

	
	public static void main(String[] args){
		send("15959371663","ssfdsfdf2222222222");
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 默认发送短信类型
	 * @param
	 * @date 2017-5-24 上午10:15:53
	 */
	public static void send(String mobile,String msg){
		send(mobile,msg,"0");
	}
	
	
	/**
	 * @author linjunqin
	 * @Description 发送短信
	 * @param mobile多个手机用,号隔开
	 * @date 2017-2-10 上午10:05:53
	 */
	public static void send(String mobile,String msg,String type){
		try {
			send(SMS_URL,SMS_ACCOUNT,SMS_PASSWORD,mobile,msg,false,null,null,type);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * 
	 * @param url 应用地址，类似于http://ip:port/msg/
	 * @param account 账号
	 * @param pswd 密码
	 * @param mobile 手机号码，多个号码使用","分割
	 * @param msg 短信内容
	 * @param needstatus 是否需要状态报告，需要true，不需要false
	 * @param type 发送类型
	 * @return 返回值定义参见HTTP协议文档
	 * @throws Exception
	 */
	public static String send(String url, String account, String pswd, String mobile, String msg,
			boolean needstatus, String product, String extno,String type) throws Exception {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod();
		try {
			URI base = new URI(url, false);
			method.setURI(new URI(base, "HttpBatchSendSM", false));
			//method.setURI(new URI(base, "HttpSendSM  HttpBatchSendSM", false));
			method.setQueryString(new NameValuePair[] { 
					new NameValuePair("account", account),
					new NameValuePair("pswd", pswd), 
					new NameValuePair("mobile", mobile),
					new NameValuePair("needstatus", String.valueOf(needstatus)), 
					new NameValuePair("msg", msg),
					new NameValuePair("product", product), 
					new NameValuePair("extno", extno), 
				});
			int result = client.executeMethod(method);
			if (result == HttpStatus.SC_OK) {
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				//加入短信发送记录
				//SmsLogFuc.sendMsgLog(mobile, msg, type, "0");//0:发送成功状态的信息
				return URLDecoder.decode(baos.toString(), "UTF-8");
			} else {
				throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
			}
		} finally {
			method.releaseConnection();
			//SmsLogFuc.sendMsgLog(mobile, msg, type, "1");//0:信息发送失败的记录
		}

	}

	/**
	 * 
	 * @param url 应用地址，类似于http://ip:port/msg/
	 * @param account 账号
	 * @param pswd 密码
	 * @param mobile 手机号码，多个号码使用","分割
	 * @param msg 短信内容
	 * @param needstatus 是否需要状态报告，需要true，不需要false
	 * @return 返回值定义参见HTTP协议文档
	 * @throws Exception
	 */
	public static String batchSend(String url, String account, String pswd, String mobile, String msg,
			boolean needstatus, String product, String extno) throws Exception {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod();
		try {
			URI base = new URI(url, false);
			method.setURI(new URI(base, "HttpBatchSendSM", false));
			method.setQueryString(new NameValuePair[] { 
					new NameValuePair("account", account),
					new NameValuePair("pswd", pswd), 
					new NameValuePair("mobile", mobile),
					new NameValuePair("needstatus", String.valueOf(needstatus)), 
					new NameValuePair("msg", msg),
					new NameValuePair("product", product),
					new NameValuePair("extno", extno), 
				});
			int result = client.executeMethod(method);
			if (result == HttpStatus.SC_OK) {
				InputStream in = method.getResponseBodyAsStream();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				return URLDecoder.decode(baos.toString(), "UTF-8");
			} else {
				throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
			}
		} finally {
			method.releaseConnection();
		}

	}
}
