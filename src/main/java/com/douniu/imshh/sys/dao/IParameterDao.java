package com.douniu.imshh.sys.dao;

import java.util.List;
import java.util.Map;

import com.douniu.imshh.sys.domain.Parameter;

public interface IParameterDao {
	List<Parameter> allParameters();
	Parameter getParameter(String pname);
	List<Map<String, String>> getDictionary(String dname);
	void setParameter(Parameter param);
	void putDictionary(String dname, String vkey, String vtext);
}
