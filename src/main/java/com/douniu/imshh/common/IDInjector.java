package com.douniu.imshh.common;

import java.lang.reflect.Field;
import java.util.List;

public class IDInjector {
	public static void injector(Object src){
		try {
			injector(src, "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void injector(List<Object> src){
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
		Field f = src.getClass().getDeclaredField("");
		if (f != null){
			f.setAccessible(true);
			f.set(src, System.currentTimeMillis()+postfix);
		}
	}
}
