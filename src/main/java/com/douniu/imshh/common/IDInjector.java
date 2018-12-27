package com.douniu.imshh.common;

import java.lang.reflect.Field;
import java.util.List;

import com.douniu.imshh.utils.ReflectionUtil;

public class IDInjector {
	public static void injector(Object src){
		try {
			injector(src, "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static <T> void injector(List<T> src){
		try {
			int i = 1;
			for (Object item : src){
				i++;
				injector(item, i+"");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void injector(Object src, String postfix) throws Exception{
		ReflectionUtil.setFieldValue(src, "id", System.currentTimeMillis()+postfix);
	}
}
