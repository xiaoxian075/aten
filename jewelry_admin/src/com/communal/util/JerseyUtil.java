package com.communal.util;

import java.util.HashMap;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class JerseyUtil {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//getToken();
		//jerseyDelete();
	}

	
	/**
	 * @author linjunqin
	 * @Description delete请求删除用户
	 * @param
	 * @date 2017-2-17 下午3:00:58
	 */
	public static String jerseyDelete(String url,HashMap<String, String> paraMap,String token){
		ClientConfig cc = new DefaultClientConfig();
		Client client = Client.create(cc);
		WebResource resource = client.resource(url);
		//JSONObject obj = new JSONObject(paraMap);
		ClientResponse response = resource.header("Authorization","Bearer "+token).accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.delete(ClientResponse.class);
		String result = response.getEntity(String.class);
		System.out.println("easemob==="+result);
		return result;
	}
	
	
	/**
	 * @author linjunqin
	 * @Description post请求
	 * @param
	 * @date 2017-2-17 下午2:58:15
	 */
	public static String jerseyPost(String url,HashMap<String, String> paraMap){
		return jerseyPost(url,paraMap,null);
	}
	public static String jerseyPost(String url,HashMap<String, String> paraMap,String token){
		//初始客户端配置
		ClientConfig cc = new DefaultClientConfig();
		Client client = Client.create(cc);
		WebResource resource = client.resource(url);
		JSONObject obj = new JSONObject(paraMap);
		//post请求
		ClientResponse response = resource.header("Authorization","Bearer "+token).accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, obj);

		String result = response.getEntity(String.class);
		System.out.println("easemob==="+result);
		return result;
	}
	
}
