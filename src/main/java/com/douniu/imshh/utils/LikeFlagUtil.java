package com.douniu.imshh.utils;

import java.lang.reflect.Field;
import java.util.List;

public class LikeFlagUtil {
	public static <T> T appendLikeFlag(T object, String[] targets){
		
		List<Field> fields = ReflectionUtil.getDeclaredFields(object, targets);
		for (Field field : fields){
			if (field.getType() == String.class){
				Object value = ReflectionUtil.getFieldValue(object, field.getName());
				if (value != null && !value.toString().trim().equals("")){
					ReflectionUtil.setFieldValue(object, field.getName(), "%" + value + "%");
				}
			}
		}		
		return object;
	}
}
