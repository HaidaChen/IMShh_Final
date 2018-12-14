package com.douniu.imshh.sys.service;

public interface IParameterService {
	String getParam(String pname);
	boolean getBoolean(String pname);
	int getInteger(String pname);
	float getFloat(String pname);
	void setParam(String pname, String pvalue);
	void setBoolean(String pname, boolean pvalue);
	void setInteger(String pname, int pvalue);
	void setFloat(String pname, float pvalue);
}
