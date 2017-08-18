package com.aten.client;

import java.io.IOException;
import java.util.HashMap;

import com.aten.function.SysconfigFuc;
import com.aten.model.bean.HcRequestData;
import com.communal.util.DesUtil;
import com.communal.util.HttpClientUtil;

import net.sf.json.JSONObject;


public class YunPayClient {

	/**
	 * @author linjunqin
	 * @Description  调用云付通接口 检测订单 是否已付款
	 * @param   1:付款成功  0：付款失败
	 * @date 2017年8月1日 下午2:56:37 
	 */
	public static String checkYunPayPayState(HashMap<String,String> paraMap){
		HcRequestData hrd =HttpClientUtil.postUrl(SysconfigFuc.getSysValue("cfg_yunpay_checkPayState"),paraMap);
		if(hrd.getStateCode()==200){
			String resultData = "";
			try {
				resultData = DesUtil.decrypt(hrd.getResult(), "12345678");
				if(resultData!=null){
						JSONObject jsonObject = JSONObject.fromObject(resultData);
						return jsonObject.getString("is");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(resultData);
		}
		return "0"; 
	}
	
	public static void main(String[] args){
		HashMap<String, String> hMap = new HashMap<String, String>();
		hMap.put("orderId", "60020170727171434496288346");
		hMap.put("terminal", "2");
		System.out.println(checkYunPayPayState(hMap));
		//{"code":"","is":0,"msg":"尚未支付","size":0,"sum":0,"time":1501572913297,"token":""}
	}
	
	
	
}
