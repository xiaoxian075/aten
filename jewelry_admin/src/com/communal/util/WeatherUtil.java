package com.communal.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class WeatherUtil {
	
	/**
	 * @author linjunqin
	 * @Description 根据城市获取天气数据百度接口
	 * @param
	 * @date 2017-3-2 下午10:30:10
	 */
	public static String getWeatherFromBaiDu(String city) {
		String baiduUrl = "";
		try {
			baiduUrl = "http://api.map.baidu.com/telematics/v3/weather?location="
					+ URLEncoder.encode(city, "UTF-8")
					+ "&output=json&ak=KDd7Sz4txG969GGuOqD4OHTA&mcode=4F:7D:6B:ED:7D:DF:B8:46:D6:96:E6:2F:7C:0B:C0:B7:D5:99:C1:D7;com.example.car";
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		StringBuffer strBuf = new StringBuffer();
		try {
			URL url = new URL(baiduUrl);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			String line = null;
			while ((line = reader.readLine()) != null)
				strBuf.append(line + " ");
			reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strBuf.toString();
	}
	
	/**
	 * @author linjunqin
	 * @Description 根据城市获取百度天气
	 * @param
	 * @date 2017-3-2 下午10:33:34
	 */
	public static String getWeatherByCity(String city) {
		String result = getWeatherFromBaiDu(city);
		//结果 转 json对象
		JSONObject obj = JSONObject.fromObject(result);
		JSONObject resultObj = new JSONObject();
		if (Integer.parseInt(obj.get("error").toString()) == 0) {
			JSONArray results = obj.getJSONArray("results");
			JSONObject results0 = results.getJSONObject(0);
			JSONArray weather_data = results0.getJSONArray("weather_data");
			JSONObject weather0 = weather_data.getJSONObject(0);
			String wind = weather0.getString("wind");
			String dayPictureUrl = weather0.getString("dayPictureUrl");
			String nightPictureUrl = weather0.getString("nightPictureUrl");
			String weather = weather0.getString("weather");
			String temperature = weather0.getString("temperature");
			String date = weather0.getString("date");
			resultObj.put("wind", wind);
			resultObj.put("dayPictureUrl", dayPictureUrl);
			resultObj.put("nightPictureUrl", nightPictureUrl);
			resultObj.put("weather", weather);
			resultObj.put("temperature", temperature);
			resultObj.put("date", date);
			return resultObj.toString();
		}
		return obj.getString("status");
	}
	
}
