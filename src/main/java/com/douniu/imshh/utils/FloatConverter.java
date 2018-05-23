package com.douniu.imshh.utils;

import org.springframework.core.convert.converter.Converter;

public class FloatConverter  implements Converter<String, Float>{

	@Override
	public Float convert(String arg0) {
		if (arg0 == null || arg0.equals("null") || arg0.trim().equals(""))
			return 0f;
		return new Float(arg0);
	}

}
