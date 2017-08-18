package com.communal.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

import net.sf.json.JSONArray;

/**
 * @function 功能 json格式的封装
 * @author 林俊钦
 * @date 创建日期: Jul 29, 2013 时间:5:52:32 PM
 */
public class JsonUtil {

	/**
	 * @function 功能:Object对象转换为json
	 * @author 林俊钦
	 * @date 创建日期: 2013-9-24 时间:下午3:15:46
	 */
	public static String object2json(Object obj) {
		StringBuilder json = new StringBuilder();
		if (obj == null) {
			json.append("\"\"");
		} else if (obj instanceof String || obj instanceof Integer
				|| obj instanceof Float || obj instanceof Boolean
				|| obj instanceof Short || obj instanceof Double
				|| obj instanceof Long || obj instanceof BigDecimal
				|| obj instanceof BigInteger || obj instanceof Byte) {
			json.append("\"").append(string2json(obj.toString())).append("\"");
		} else if (obj instanceof Object[]) {
			json.append(array2json((Object[]) obj));
		} else if (obj instanceof List) {
			json.append(list2json((List<?>) obj));
		} else if (obj instanceof Map) {
			json.append(map2json((Map<?, ?>) obj));
		} else if (obj instanceof Set) {
			json.append(set2json((Set<?>) obj));
		} else {
			json.append(bean2json(obj));
		}
		return json.toString();
	}

	/**
	 * @function 功能:bean对象转换为json
	 * @author 林俊钦
	 * @date 创建日期: 2013-9-24 时间:下午3:15:46
	 */
	public static String bean2json(Object bean) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(bean.getClass(), Object.class)
					.getPropertyDescriptors();
		} catch (IntrospectionException e) {
		}
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				try {
					String name = object2json(props[i].getName());
					String value = object2json(props[i].getReadMethod().invoke(
							bean));
					json.append(name);
					json.append(":");
					json.append(value);
					json.append(",");
				} catch (Exception e) {
				}
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	/**
	 * @function 功能:List对象转换为json
	 * @author 林俊钦
	 * @date 创建日期: 2013-9-24 时间:下午3:15:46
	 */
	public static String list2json(List<?> list) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				json.append(object2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	/**
	 * @function 功能:array转换为json
	 * @author 林俊钦
	 * @date 创建日期: 2013-9-24 时间:下午3:15:46
	 */
	public static String array2json(Object[] array) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (array != null && array.length > 0) {
			for (Object obj : array) {
				json.append(object2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	/**
	 * @function 功能:map转换为json
	 * @author 林俊钦
	 * @date 创建日期: 2013-9-24 时间:下午3:15:46
	 */
	public static String map2json(Map<?, ?> map) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		if (map != null && map.size() > 0) {
			for (Object key : map.keySet()) {
				json.append(object2json(key));
				json.append(":");
				json.append(object2json(map.get(key)));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	/**
	 * @function 功能:Set转换为json
	 * @author 林俊钦
	 * @date 创建日期: 2013-9-24 时间:下午3:15:46
	 */
	public static String set2json(Set<?> set) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (set != null && set.size() > 0) {
			for (Object obj : set) {
				json.append(object2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	/**
	 * @function 功能:String转换为json
	 * @author 林俊钦
	 * @date 创建日期: 2013-9-24 时间:下午3:15:46
	 */
	public static String string2json(String s) {
		if (s == null)
			return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			switch (ch) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
				if (ch >= '\u0000' && ch <= '\u001F') {
					String ss = Integer.toHexString(ch);
					sb.append("\\u");
					for (int k = 0; k < 4 - ss.length(); k++) {
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				} else {
					sb.append(ch);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * @function 功能:json转换为list
	 * @author 林俊钦
	 * @date 创建日期: Sep 25, 2013 时间:10:55:59 AM
	 */
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public static List json2list(String json) {
		List list = new ArrayList();
		JSONArray jsonArray = JSONArray.fromObject(json);
		list = (List) JSONArray.toList(jsonArray);
		return list;
	}

	/**
	 * @function 功能 json串转换成数组
	 * @author 
	 * @date 创建日期: Sep 25, 2013 时间:2:32:55 PM
	 */
	public static JSONArray json2Array(String json) {
		JSONArray jsonArray = JSONArray.fromObject(json);
		return jsonArray;
	}
	
	private static Gson gson = new Gson();
	public static <T> String jsonToStr(T t) {
		return gson.toJson(t);
	}
	public static <T> T strToJson(String str,Type type) {
		return gson.fromJson(str, type);
	}

}
