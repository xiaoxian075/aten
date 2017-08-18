package com.inter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AtyNet {

	//编码格式。发送编码格式统一用UTF-8
	private static String ENCODING = "UTF-8";

	//查屏蔽词
	private static final String GET_BLACK_WORD = "http://sms.haotingyun.com/v2/sms/get_black_word.json";


	//获取模板
	private static final String GET_MODEL = "http://sms.haotingyun.com/v2/tpl/get.json";
	//添加模板
	private static final String ADD_MODEL = "http://sms.haotingyun.com/v2/tpl/add.json";
	//修改模板
	private static final String UPD_MODEL = "http://sms.haotingyun.com/v2/tpl/update.json";
	//删除模板
	private static final String DEL_MODEL = "http://sms.haotingyun.com/v2/tpl/del.json";
	//获取默认模板
	private static final String GET_DEFAULT_MODEL = "http://sms.haotingyun.com/v2/tpl/get_default.json";

	//单条发送
	private static final String SINGLESEND_SMS = "http://sms.haotingyun.com/v2/sms/single_send.json";
	//批量发送
	private static final String BATCHSEND_SMS = "http://sms.haotingyun.com/v2/sms/batch_send.json";


	/**
	 * 查屏蔽词
	 * @param apikey
	 * @param text
	 * @return
	 */
	public static ReqMsg<List<String>> getBlackWord(String apikey, String text) {
		if (StringUtils.isBlank(apikey) || StringUtils.isBlank(text))
			return new ReqMsg<List<String>>(1001,"参数出错",null);

		Map<String,String> params = new HashMap<String,String>();
		params.put("apikey", apikey);
		params.put("text", text);
		return postList(GET_BLACK_WORD,params);
	}

	/**
	 * 短信单发
	 * @param apikey
	 * @param mobile
	 * @param text
	 * @return
	 */
	public static ReqMsg<SmsInfoNode> singleSendSms(String apikey, String mobile, String text) {
		if (StringUtils.isBlank(apikey) || StringUtils.isBlank(mobile) || StringUtils.isBlank(text))
			return new ReqMsg<SmsInfoNode>(1001,"参数出错",null);

		Map<String,String> params = new HashMap<String,String>();
		params.put("apikey", apikey);
		params.put("mobile", mobile);
		params.put("text", text);
		return postObj(SINGLESEND_SMS,params,SmsInfoNode.class);
	}

	/**
	 * 短信群发
	 * @param apikey
	 * @param arrMobile
	 * @param text
     * @return
     */
	public static ReqMsg<SmsInfoList> batchSendSms(String apikey, List<String> arrMobile, String text) {
		if (StringUtils.isBlank(apikey) || arrMobile==null || arrMobile.size()==0 || arrMobile.size()>1000 || StringUtils.isBlank(text))
			return new ReqMsg<SmsInfoList>(1001,"参数出错",null);

		String mobile = StringUtils.join(arrMobile, ",");

		Map<String,String> params = new HashMap<String,String>();
		params.put("apikey", apikey);
		params.put("mobile", mobile);
		params.put("text", text);
		return postObj(BATCHSEND_SMS,params,SmsInfoList.class);
	}

	/**
	 * 获取模板
	 */
	public static ReqMsg<List<ModelNode>> getModel(String apikey) {
		if (StringUtils.isBlank(apikey))
			return new ReqMsg<List<ModelNode>>(1001,"参数出错",null);

		Map<String,String> params = new HashMap<String,String>();
		params.put("apikey", apikey);
		return postList(GET_MODEL,params);
	}

	/**
	 * 获取模板
	 */
	public static ReqMsg<ModelNode> getModel(String apikey, long tplId) {
		if (StringUtils.isBlank(apikey) || tplId<0)
			return new ReqMsg<ModelNode>(1001,"参数出错",null);

		Map<String,String> params = new HashMap<String,String>();
		params.put("apikey", apikey);
		params.put("tpl_id", String.valueOf(tplId));
		return postObj(GET_MODEL,params,ModelNode.class);
	}

	/**
	 * 添加模板
	 */
	public static ReqMsg<ModelNode> addModel(String apikey, String content) {
		if (StringUtils.isBlank(apikey) || StringUtils.isBlank(content))
			return new ReqMsg<ModelNode>(1001,"参数出错",null);

		Map<String,String> params = new HashMap<String,String>();
		params.put("apikey", apikey);
		params.put("tpl_content", content);
		return postObj(ADD_MODEL,params,ModelNode.class);

	}

	/**
	 * 更新模板
	 * @param tplId
	 * @param sign
	 * @param content
	 * @return
	 */
	public static ReqMsg<ModelNode> updModel(String apikey, long tplId, String content) {
		if (StringUtils.isBlank(apikey) || tplId<0 || StringUtils.isBlank(content))
			return new ReqMsg<ModelNode>(1001,"参数出错",null);

		Map<String,String> params = new HashMap<String,String>();
		params.put("apikey", apikey);
		params.put("tpl_id", String.valueOf(tplId));
		params.put("tpl_content", content);
		return postObj(UPD_MODEL,params,ModelNode.class);
	}

	/**
	 * 删除模板
	 * @param tplId
	 * @return
	 */
	public static ReqMsg<ModelNode> delModel(String apikey, long tplId) {
		if (StringUtils.isBlank(apikey) || tplId<0)
			return new ReqMsg<ModelNode>(1001,"参数出错",null);

		Map<String,String> params = new HashMap<String,String>();
		params.put("apikey", apikey);
		params.put("tpl_id", String.valueOf(tplId));
		return postObj(DEL_MODEL,params,ModelNode.class);
	}

	/**
	 * 获取默认模板
	 */
	public static ReqMsg<List<ModelNode>> getDefModel(String apikey) {
		if (StringUtils.isBlank(apikey))
			return new ReqMsg<List<ModelNode>>(1001,"参数出错",null);

		Map<String,String> params = new HashMap<String,String>();
		params.put("apikey", apikey);
		return postList(GET_DEFAULT_MODEL,params);
	}

	/**
	 * 获取模板
	 */
	public static ReqMsg<ModelNode> getDefModel(String apikey, long tplId) {
		if (StringUtils.isBlank(apikey) || tplId<0)
			return new ReqMsg<ModelNode>(1001,"参数出错",null);

		Map<String,String> params = new HashMap<String,String>();
		params.put("apikey", apikey);
		params.put("tpl_id", String.valueOf(tplId));
		ReqMsg<List<ModelNode>> reqmsg = postList(GET_DEFAULT_MODEL,params);
		if (reqmsg.getCode()==0) {
			List<ModelNode> list = reqmsg.getT();
			if (list==null || list.size()!=1) {
				return new ReqMsg<ModelNode>(1004,"解析出错",null);
			}
			return new ReqMsg<ModelNode>(1004,"解析出错",list.get(0));
		} else {
			return new ReqMsg<ModelNode>(reqmsg.getCode(),reqmsg.getDesc(),null);
		}
	}







	public static int getState(String status) {
		if (StringUtils.isBlank(status))
			return 0;
		if ("SUCCESS".equals(status))
			return 1;
		else if ("CHECKING".equals(status))
			return 2;
		else if ("FAIL".equals(status))
			return 3;

		return 0;
	}


	private static <T> ReqMsg<T> postObj(String url, Map<String,String> params, Class<T> c) {
		try {
			String data = _post(url,params);
			//String data = "{\"total_count\":2,\"total_fee\":\"0.1000\",\"unit\":\"RMB\",\"data\":[{ \"code\":0,\"count\":1,\"fee\":0.05,\"mobile\":\"15229125192\",\"msg\":\"发送成功\",\"sid\":16975690812,\"unit\":\"RMB\"},{\"code\":0,\"count\":1,\"fee\":0.05,\"mobile\":\"13255984019\",\"msg\":\"发送成功\",\"sid\":16975690810,\"unit\":\"RMB\"}]}";
			if (data==null || data.length()<8)
				return new ReqMsg<T>(1002,"接口调用出错",null);

			JSONObject json=JSONObject.parseObject(data);
			int code = json.getIntValue("code");
			if (code==0) {

				T t = toJson(data, c);
				return new ReqMsg<T>(0,"正确",t);
			} else {
				String msg = json.getString("msg");
				String detail = json.getString("detail");
				return new ReqMsg<T>(code,msg+"--"+detail,null);
			}

		} catch (Exception e) {
			return new ReqMsg<T>(1001,"异常",null);
		}
	}

	private static <T> ReqMsg<List<T>> postList(String url, Map<String,String> params) {
		try {
			String data = _post(url,params);
			if (data==null || data.length()<2)
				return new ReqMsg<List<T>>(1002,"接口调用出错",null);
			if (data.startsWith("[")) {
				List<T> list = toJson(data, new TypeToken<List<T>>() {}.getType());
				return new ReqMsg<List<T>>(0,"正确",list);
			} else if (data.startsWith("{")) {
				JSONObject json=JSONObject.parseObject(data);
				int code = json.getIntValue("code");
				String msg = json.getString("msg");
				String detail = json.getString("detail");
				return new ReqMsg<List<T>>(code,msg+"--"+detail,null);
			} else {
				return new ReqMsg<List<T>>(1003,"异常",null);
			}

		} catch (Exception e) {
			return new ReqMsg<List<T>>(1001,"异常",null);
		}
	}

	/**
	 * 基于HttpClient 4.3的通用POST方法
	 *
	 * @param url       提交的URL
	 * @param paramsMap 提交<参数，值>Map
	 * @return 提交响应
	 */

	private static String _post(String url, Map<String, String> paramsMap) {
		CloseableHttpClient client = HttpClients.createDefault();
		String responseText = "";
		CloseableHttpResponse response = null;
		try {
			HttpPost method = new HttpPost(url);
			if (paramsMap != null) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> param : paramsMap.entrySet()) {
					NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
					paramList.add(pair);
				}
				method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
			}
			response = client.execute(method);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseText = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return responseText;
	}

	private static Gson gson = new Gson();

//	private static <T> String toString(T t) {
//		return gson.toJson(t);
//	}

	private static <T> T toJson(String data,Type type) {
		return gson.fromJson(data, type);
	}
}
