package com.communal.thirdservice.logistics;

import java.util.ArrayList;

/**
 * @author chenjx
 * @date 2017-07-15
 */


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.communal.node.ReqMsg;
import com.communal.util.HttpUtils;

public class Logistics {

	private static String host;
	private static String path;
	private static String appcode;
	
	public static boolean init() {
		host = "https://ali-deliver.showapi.com";
		path = "/showapi_expInfo";
		appcode = "ee52535af5d7493f9955394edb4b37d8";
		//AppKey：24545180     
		//AppSecret：66584468349ffa6caa17dd8df9b277c1 
		return true;
	}
	
	public static void main(String[] args) {
	    String com = "auto";
	    String nu = "883420070249072469";
	    ReqMsg<LogisticsNode> reqmsg = get(com,nu);
	    if (reqmsg!=null) {
	    	LogisticsNode logistics = reqmsg.getInfo();
	    	if (logistics!=null) {
	    		System.out.println(logistics);
	    		return;
	    	}
	    }
	    System.out.println("fail");
	}

	/**
	 * 
	 * @param appcode
	 * @param com 	快递公司字母简称  如不知道快递公司名,可以使用"auto"代替 auto
	 * @param nu	快递单号 883420070249072469
	 */
	public static ReqMsg<LogisticsNode> get(String com,String nu) {
		if (
				StringUtils.isBlank(host) ||
				StringUtils.isBlank(path) ||
				StringUtils.isBlank(appcode)) {
			if (!init()) {
				return new ReqMsg<LogisticsNode>(1,"参数初始化错误",null);
			}
		}
	    String method = "GET";
	    Map<String, String> headers = new HashMap<String, String>();
	    headers.put("Authorization", "APPCODE " + appcode); //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("com", com);	//快递公司字母简称  如不知道快递公司名,可以使用"auto"代替
	    querys.put("nu", nu);	//快递单号

	    LogisticsNode logisticsNode = null;
	    try {
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	String body = EntityUtils.toString(response.getEntity());//获取response的body
	    	
	    	//String body = "{\"showapi_res_code\":0,\"showapi_res_error\":\"\",\"showapi_res_body\":{\"mailNo\":\"883420070249072469\",\"update\":1500099780609,\"updateStr\":\"2017-07-15 14:23:00\",\"ret_code\":0,\"flag\":true,\"dataSize\":9,\"status\":4,\"tel\":\"021-69777888/999\",\"expSpellName\":\"yuantong\",\"data\":[{\"time\":\"2016-11-17 12:47:59\",\"context\":\"客户 签收人 :已签收，签收人凭取货码签收。 已签收  感谢使用圆通速递，期待再次为您服务\"},{\"time\":\"2016-11-16 09:20:27\",\"context\":\"云南省昆明市呈贡区大学城公司(点击查询电话)夏** 派件中 派件员电话15912462535\"},{\"time\":\"2016-11-16 09:16:16\",\"context\":\"云南省昆明市呈贡区大学城公司 已收入\"},{\"time\":\"2016-11-16 02:17:22\",\"context\":\"昆明转运中心 已发出,下一站 云南省昆明市呈贡区大学城\"},{\"time\":\"2016-11-13 04:30:12\",\"context\":\"济南转运中心 已发出,下一站 昆明转运中心\"},{\"time\":\"2016-11-13 04:28:11\",\"context\":\"济南转运中心 已收入\"},{\"time\":\"2016-11-12 22:47:41\",\"context\":\"山东省聊城市公司 已发出,下一站 济南转运中心\"},{\"time\":\"2016-11-12 20:46:00\",\"context\":\"山东省聊城市公司 已打包\"},{\"time\":\"2016-11-12 19:48:36\",\"context\":\"山东省聊城市公司(点击查询电话) 已揽收\"}],\"expTextName\":\"圆通速递\"}}";
	    	
	    	JSONObject json=JSONObject.parseObject(body);
	    	int showapi_res_code = json.getIntValue("showapi_res_code");
	    	if (showapi_res_code!=0) {
	    		String showapi_res_error = json.getString("showapi_res_error");
	    		return new ReqMsg<LogisticsNode>(1,showapi_res_error,null);
	    	}
	    	JSONObject showapi_res_body = json.getJSONObject("showapi_res_body");
	    	int ret_code = showapi_res_body.getIntValue("ret_code");
	    	boolean flag = showapi_res_body.getBooleanValue("flag");
	    	if (ret_code!=0 || !flag) {
	    		String msg = showapi_res_body.getString("msg");
	    		return new ReqMsg<LogisticsNode>(2,msg,null);
	    	}
	    	
	    	String mailNo = showapi_res_body.getString("mailNo");
	    	String updateStr = showapi_res_body.getString("updateStr");
	    	int status = showapi_res_body.getIntValue("status");
	    	String tel = showapi_res_body.getString("tel");
	    	String expSpellName = showapi_res_body.getString("expSpellName");
	    	String expTextName = showapi_res_body.getString("expTextName");
	    	List<LogisticsChildNode> data = new ArrayList<LogisticsChildNode>();
	    	JSONArray jsonData = showapi_res_body.getJSONArray("data");
	    	Iterator<Object> iter = jsonData.iterator();
	    	while (iter.hasNext()) {
	    		JSONObject node = (JSONObject)iter.next();
	    		data.add(new LogisticsChildNode(node.getString("context"),node.getString("time")));
	    	}
	    	
	    	logisticsNode = new LogisticsNode(mailNo,updateStr,status,tel,expSpellName,expTextName,data);
	    } catch (Exception e) {
	    	logisticsNode = null;
	    }
	    
	    if (logisticsNode==null) {
	    	return new ReqMsg<LogisticsNode>(99,"获取失败，异常",null);
	    } else {
	    	return new ReqMsg<LogisticsNode>(0,"succ",logisticsNode);
	    }
	}
}

/*
{
	"showapi_res_code": 0,//showapi平台返回码,0为成功,其他为失败
	"showapi_res_error": "",//showapi平台返回的错误信息
	"showapi_res_body": {
		"mailNo": "968018776110",//快递单号
		"update": 1466926312666,//数据最后查询的时间
		"updateStr": "2016-06-26 15:31:52",//数据最后更新的时间
		"ret_code": 0,//接口调用是否成功,0为成功,其他为失败
		"flag": true,//物流信息是否获取成功
		"status": 4,-1 待查询 0 查询异常 1 暂无记录 2 在途中 3 派送中 4 已签收 5 用户拒签 6 疑难件 7 无效单 8 超时单 9 签收失败 10 退回
		"tel": "400-889-5543",//快递公司电话
		"expSpellName": "shentong",//快递字母简称
		"data": [//具体快递路径信息
			{
				"time": "2016-06-26 12:26",
				"context": "已签收,签收人是:【本人】"
			},
			{
				"time": "2016-06-25 15:31",
				"context": "【陕西陇县公司】的派件员【西城业务员】正在派件"
			},
			{
				"time": "2016-06-25 14:11",
				"context": "快件已到达【陕西陇县公司】"
			},
			{
				"time": "2016-06-25 09:08",
				"context": "由【陕西宝鸡公司】发往【陕西陇县公司】"
			},
			{
				"time": "2016-06-24 14:08",
				"context": "由【陕西西安中转部】发往【陕西宝鸡公司】"
			},
			{
				"time": "2016-06-22 13:23",
				"context": "由【山东临沂公司】发往【陕西西安中转部】"
			},
			{
				"time": "2016-06-21 23:02",
				"context": "【江苏常熟公司】正在进行【装袋】扫描"
			},
			{
				"time": "2016-06-21 23:02",
				"context": "由【江苏常熟公司】发往【江苏江阴航空部】"
			},
			{
				"time": "2016-06-21 18:30",
				"context": "【江苏常熟公司】的收件员【严继东】已收件"
			},
			{
				"time": "2016-06-21 16:41",
				"context": "【江苏常熟公司】的收件员【凌明】已收件"
			}
		],
                "possibleExpList": [//当auto查询失败的时候,返回此信息,成功时不返回
                                    //用户表示该单号可能属于那些快递物流公司
                        {
                                 "simpleName": "shunfeng",//快递公司简称
                                 "expName": "顺丰速运"
                         }
                 ],
		"expTextName": "申通快递"//快递公司名
	}
}
{
	"showapi_res_code":0,
	"showapi_res_error":"",
	"showapi_res_body":{
		"update":1500067894051,
		"updateStr":"2017-07-15 05:31:34",
		"dataSize":0,
		"status":1,
		"tel":"021-69777888/999",
		"data":[],
		"expSpellName":"yuantong",
		"msg":"查询失败,请指定单号对应的快递公司后再试!",
		"mailNo":"883420070249072468",
		"ret_code":0,
		"flag":false,
		"expTextName":"圆通速递",
		"possibleExpList":[{"simpleName":"yuantong","expName":"圆通速递"}]
	}
}


{
	"showapi_res_code":0,
	"showapi_res_error":"",
	"showapi_res_body":{
		"ret_code":-1,
		"flag":false,
		"data":[],
		"msg":"未查到单号对应的快递公司,请指定具体的快递公司简称."
	}
}

{
	"showapi_res_code":0,
	"showapi_res_error":"",
	"showapi_res_body":{
		"mailNo":"883420070249072469",
		"update":1500067621557,
		"updateStr":"2017-07-15 05:27:01",
		"ret_code":0,
		"flag":true,
		"dataSize":9,
		"status":4,
		"tel":"021-69777888/999",
		"data":[
			{
				"time":"2016-11-17 12:47:59",
				"context":"客户 签收人 :已签收，签收人凭取货码签收。 已签收  感谢使用圆通速递，期待再次为您服务"
			},
			{
				"time":"2016-11-16 09:20:27",
				"context":"云南省昆明市呈贡区大学城公司(点击查询电话)夏** 派件中 派件员电话15912462535"
			},
			{
				"time":"2016-11-16 09:16:16",
				"context":"云南省昆明市呈贡区大学城公司 已收入"
			},
			{
				"time":"2016-11-16 02:17:22",
				"context":"昆明转运中心 已发出,下一站 云南省昆明市呈贡区大学城"
			},
			{
				"time":"2016-11-13 04:30:12",
				"context":"济南转运中心 已发出,下一站 昆明转运中心"
			},
			{
				"time":"2016-11-13 04:28:11",
				"context":"济南转运中心 已收入"
			},
			{
				"time":"2016-11-12 22:47:41",
				"context":"山东省聊城市公司 已发出,下一站 济南转运中心"
			},
			{
				"time":"2016-11-12 20:46:00",
				"context":"山东省聊城市公司 已打包"
			},
			{
				"time":"2016-11-12 19:48:36",
				"context":"山东省聊城市公司(点击查询电话) 已揽收"
			}
		],
		"expSpellName":"yuantong",
		"expTextName":"圆通速递"
	}
}
*/
