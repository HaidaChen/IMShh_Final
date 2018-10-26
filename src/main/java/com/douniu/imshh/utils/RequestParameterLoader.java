package com.douniu.imshh.utils;

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class RequestParameterLoader {
	public static void loadParameter(HttpServletRequest request, Object target, String[] filedNames){
		List<Field> fields = ReflectionUtil.getDeclaredFields(target, filedNames);
		for (Field field : fields){
			String name = field.getName();
			String value = request.getParameter(name);
			if (value != null)
				ReflectionUtil.setFieldValue(target, name, value);
		}
	}	
}
