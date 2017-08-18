package com.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class JsonUtil {

	public static Gson gson = new Gson();
	
	public static <T> String toString(T t) {
		return gson.toJson(t);
	}
	
	public static <T> T toJson(String data,Type type) {
		return gson.fromJson(data, type);
	}
}