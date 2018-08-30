package com.douniu.imshh.finance.dashboard.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.finance.dashboard.domain.MaterialIndicators;
import com.douniu.imshh.finance.dashboard.domain.MaterialStatistics;
import com.douniu.imshh.finance.dashboard.service.IMaterialViewService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/materialView")
public class MaterialViewAction {
	
	@Autowired
	private IMaterialViewService service;
	
	@RequestMapping(value ="/loadmaterialview", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadMaterialView(MaterialStatistics mtlView){
		List<MaterialStatistics> res = service.queryMaterial(mtlView);
		int count = service.countMaterial(mtlView);
		
		PageResult pr = new PageResult();
		
		pr.setTotal(count);
		pr.setRows(res);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gson.toJson(pr);
	}
	
	@RequestMapping(value ="/loadmaterialindicators", produces = "application/json; charset=utf-8")
	@ResponseBody
	private String loadMaterialIndicators(String year){
		MaterialIndicators indicators = service.getIndicators(year);
		if (indicators == null){
			indicators = new MaterialIndicators();
		}
		Gson gson = new Gson();
		return gson.toJson(indicators);
	}
}
