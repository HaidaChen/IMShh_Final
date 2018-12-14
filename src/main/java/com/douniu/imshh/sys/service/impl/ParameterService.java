package com.douniu.imshh.sys.service.impl;

import com.douniu.imshh.sys.dao.IParameterDao;
import com.douniu.imshh.sys.domain.Parameter;
import com.douniu.imshh.sys.service.IParameterService;

public class ParameterService implements IParameterService {
	private IParameterDao dao;
	
	@Override
	public String getParam(String pname) {
		Parameter param = dao.getParameter(pname);
		return param.getPvalue();
	}

	@Override
	public boolean getBoolean(String pname) {
		String val = getParam(pname);
		return new Boolean(val);
	}

	@Override
	public int getInteger(String pname) {
		String val = getParam(pname);
		return new Integer(val);
	}

	@Override
	public float getFloat(String pname) {
		String val = getParam(pname);
		return new Float(val);
	}

	@Override
	public void setParam(String pname, String pvalue) {
		Parameter param = new Parameter();
		param.setPname(pname);
		param.setPvalue(pvalue);
		dao.setParameter(param);
	}

	@Override
	public void setBoolean(String pname, boolean pvalue) {
		setParam(pname, new Boolean(pvalue).toString());
	}

	@Override
	public void setInteger(String pname, int pvalue) {
		setParam(pname, new Integer(pvalue).toString());
	}

	@Override
	public void setFloat(String pname, float pvalue) {
		setParam(pname, new Float(pvalue).toString());
	}

	public void setDao(IParameterDao dao) {
		this.dao = dao;
	}
}
