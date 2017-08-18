package com.communal.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import com.aten.model.bean.HcRequestData;
import com.communal.util.HttpClientUtil;

public class HttpClientUtil {

	private static final Logger logger = Logger.getLogger(HttpClientUtil.class);
	
	public static void main(String[] args){
		
		HashMap<String, String> paraMap = new HashMap<String, String>();
			postUrl("http://localhost:9200/index1",paraMap);
	}
	
	/**
	 * @author linjunqin
	 * @Description httpclient post链接请求
	 * @param
	 * @date 2017-2-9 上午9:50:33
	 */
	public static HcRequestData postUrl(String url, HashMap<String, String> paraMap) {
		//建立 httpClient 请求
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		//封装请求返回数据
		HcRequestData hrd = new HcRequestData();
		try {
			//设置头信息
			postMethod.setRequestHeader("ContentType",
					"application/x-www-form-urlencoded;charset=UTF-8");
			//请求参数
			//System.out.println(paraMap+"==========");
			NameValuePair[] data= mapToNameValuePair(paraMap);
			//设置参数
			postMethod.setRequestBody(data);
			//返回状态
			int statusCode = 0;
			try {
				statusCode = httpClient.executeMethod(postMethod);
			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("调用接口失败!");
			}
			hrd.setStateCode(statusCode);
			//请求成功
			if (statusCode == 200){
				try {
					String result = postMethod.getResponseBodyAsString();
					hrd.setResult(result);
					logger.info(result);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				logger.info("请求返回状态：" + statusCode);
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		} finally {
			//关闭链接
			postMethod.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0L);
		}
		return hrd;
	}
	
	
	/**
	 * @author linjunqin
	 * @Description map转成NameValuePair数组类型
	 * @param
	 * @date 2017-2-9 上午9:40:05
	 */
	private static NameValuePair[] mapToNameValuePair(HashMap<String, String> hMap){
		NameValuePair[] nameValuePairs = new NameValuePair[hMap.size()];
		int i=0;
		for(Map.Entry<String, String> entry:hMap.entrySet()){
			nameValuePairs[i++] = new NameValuePair(entry.getKey(),entry.getValue());
		}
		return nameValuePairs;
	}
		
}
