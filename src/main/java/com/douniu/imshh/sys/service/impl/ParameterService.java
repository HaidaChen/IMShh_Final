package com.douniu.imshh.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public Map<String, String> getDictionary(String dname) {
		Map<String, String> res = new HashMap<>();
		List<Map<String, String>> maps = dao.getDictionary(dname);
		
		String key = "";
		String value = "";
		for (Map<String, String> map : maps){
			for (Map.Entry<String,String>  entry : map.entrySet()){
				if ("vkey".equals(entry.getKey())){
					key = entry.getValue();
				} 
				if ("vtext".equals(entry.getKey())){
					value = entry.getValue();
				}
			} 
			res.put(key, value);
		}
		return res;
	}

	@Override
	public void putDictionary(String dname, String key, String text) {
		Map<String, String> map = getDictionary(dname);
		if (text.equals(map.get(key))){
			return;
		}
		dao.putDictionary(dname, key, text);
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
