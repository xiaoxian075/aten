package com.aten.function;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonDealFuc implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @author linjunqin
	 * @Description Map类型转Json字符串
	 * @param
	 * @date 2016-12-30 下午5:22:58
	 */
	public static String mapToJsonStr(HashMap<String,Object> hashMap){
		JSONObject jsonResult =  JSONObject.fromObject(hashMap);
		System.out.println(jsonResult.toString());
		return jsonResult.toString();
	}
	
	/**
	 * @author linjunqin
	 * @param <T>
	 * @Description list的对象转成json串
	 * @param
	 * @date 2016-12-30 下午5:34:10
	 */
	public <T> String obListToStr(List<T> list) {  
        JSONArray jsonarray = JSONArray.fromObject(list);  
        return jsonarray.toString();  
    }  
      

	
}