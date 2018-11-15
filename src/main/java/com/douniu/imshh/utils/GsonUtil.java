package com.douniu.imshh.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
	public static String toJson(Object target){
		Gson gson = new Gson();
		return gson.toJson(target);
	} 
	
	public static String toJson(Object target, String dateFormat){
		Gson gson = null;
		if (dateFormat == null || dateFormat.equals(""))
			gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		else
			gson = new GsonBuilder().setDateFormat(dateFormat).create();
		return gson.toJson(target);
	}
}
