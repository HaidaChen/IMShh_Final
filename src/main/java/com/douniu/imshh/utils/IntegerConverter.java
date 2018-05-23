package com.douniu.imshh.utils;

import org.springframework.core.convert.converter.Converter;

public class IntegerConverter implements Converter<String, Integer>{

	@Override
	public Integer convert(String arg0) {
		// TODO Auto-generated method stub
		if (arg0 == null || arg0.equals("null") || arg0.trim().equals(""))
			return 0;
		return new Integer(arg0);
	}

}
