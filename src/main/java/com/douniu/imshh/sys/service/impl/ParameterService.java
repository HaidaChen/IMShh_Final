package com.douniu.imshh.sys.service.impl;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.douniu.imshh.sys.dao.IParameterDao;
import com.douniu.imshh.sys.domain.Parameter;
import com.douniu.imshh.sys.service.IParameterService;
import com.douniu.imshh.utils.DateUtil;

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
	public Map<String, String> getBillPeriod() {
		Map<String, String> period = new TreeMap<>(new Comparator<String>() {
            public int compare(String obj1, String obj2) {
                // 降序排序
                return obj2.compareTo(obj1);
            }
        });
		
		Date date = new Date();
		for (int i = 0; i < 13; i++){
			SimpleDateFormat periodFormat = new SimpleDateFormat("yyyyMM");
	        SimpleDateFormat periodTextFormat = new SimpleDateFormat("yyyy年第MM期");
	        String periodYM = periodFormat.format(date);
	        String periodText = periodTextFormat.format(date);
	        period.put(periodYM, periodText);
	        date = DateUtil.getPreMonth(date);
		}
		return period;
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
