package com.douniu.imshh.sys.dao;

import java.util.List;

import com.douniu.imshh.sys.domain.Parameter;

public interface IParameterDao {
	List<Parameter> allParameters();
	Parameter getParameter(String pname);
	void setParameter(Parameter param);
}
