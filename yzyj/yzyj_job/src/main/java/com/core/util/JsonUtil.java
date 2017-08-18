package com.core.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class JsonUtil {

	public static Gson gson = new Gson();
	
	public static <T> String toString(T t) {
		return gson.toJson(t);
	}

	//new TypeToken<CommonJson4List<clazz>>() {}.getType();
	public static <T> T toJson(String data,Type type) {
		return gson.fromJson(data, type);
	}
}