package com.douniu.imshh.sys.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.douniu.imshh.sys.service.IParameterService;

public class ParameterService implements IParameterService {
	private static Map<String, Object> parameters = new HashMap<>();
	static{
		parameters.put("debug", true);
	}
	
	@Override
	public String getParam(String pname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getBoolean(String pname) {
		Object param = parameters.get(pname);
		if (null != param && param instanceof Boolean){
			return (boolean)param;
		}
		return false;
	}

	@Override
	public int getInteger(String pname) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFloat(String pname) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
