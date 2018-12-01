package com.douniu.imshh.sys.service;

public interface IParameterService {
	String getParam(String pname);
	boolean getBoolean(String pname);
	int getInteger(String pname);
	float getFloat(String pname);
}
