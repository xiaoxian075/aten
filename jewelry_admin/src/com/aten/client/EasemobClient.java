package com.aten.client;

import java.util.HashMap;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.communal.util.JerseyUtil;


public class EasemobClient {

	private static final Logger logger = Logger.getLogger(EasemobClient.class);
	
	private static String token = null;//由服务器返回
	//环信请求地址 
	private static String easeUrl ="https://a1.easemob.com";
	//环信app key 
	private static String appkey="/1111170217115365/yfqc";
	//环信授权类型 
	private static String grant_type="client_credentials";
	//环信客户ID
	private static String client_id="YXA6iBh4wPSyEeaw62WgCiCvXA";
	//环信客户密钥
	private static String client_secret="YXA6SeV8y-z0L03vk9fsDRitOlNjQCA";
		

	/**
	 * @author linjunqin
	 * @Description 获取token
	 * @param
	 * @date 2017-2-17 下午3:25:23
	 */
	public static void getToken(){
		String url = easeUrl+appkey+"/token";
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("grant_type", grant_type);
		paraMap.put("client_id", client_id);
		paraMap.put("client_secret", client_secret);
		String result = JerseyUtil.jerseyPost(url, paraMap);
		try {
			JSONObject tokenObj =  new JSONObject(result);
			token = tokenObj.get("access_token").toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author linjunqin
	 * @Description 添加环信帐号密码
	 * @param
	 * @date 2017-3-7 下午3:21:09
	 */
	public static boolean addEaseMobUser(String ease_user_name,String ease_password){
		getToken();
		String url = easeUrl+appkey+"/users";
		HashMap<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("username",ease_user_name);
		paraMap.put("password",ease_password);
		String result = JerseyUtil.jerseyPost(url, paraMap,token);
		//转成json对象
		try {
			JSONObject userObj =  new JSONObject(result);
			//如果注册出现问题
			if(!userObj.isNull("error")){
				String error = userObj.get("error").toString();
				logger.info(error);
				if(error.equals("unauthorized")){
					System.out.println("token过期 未授权");
				}else if(error.equals("duplicate_unique_property_exists")){
					System.out.println("帐户已注册");
				}
			}else{
				logger.info("环信帐号注册成功");
				return true;
			}
		} catch (JSONException e) {
			logger.info("环信帐号注册失败");
			e.printStackTrace();
		}finally{
			logger.info("环信注册业务");
		}
		return false;
	}

	
	/**
	 * @author linjunqin
	 * @Description 环信删除用户
	 * @param
	 * @date 2017-3-7 下午4:13:31
	 */
	public static void delEaseMobUser(String ease_user_name){
		getToken();
		String url = easeUrl+appkey+"/users/";
		url = url + ease_user_name;
		String result = JerseyUtil.jerseyDelete(url, null,token);
		System.out.println(result);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		getToken();
		addEaseMobUser("F3358A93CE924BD22111CAA7E69490C7","64975D76572A1F1EABAC571410B661A5");
	}
	
}

