package com.douniu.imshh.finance.storage.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.finance.storage.domain.MaterialStorage;
import com.douniu.imshh.finance.storage.service.IMaterialStorageService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MaterialStorageAction {
	
	@Autowired
	private IMaterialStorageService service;
	
	@RequestMapping(value ="/loadmaterialstorage", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadMaterialStorage(MaterialStorage storage){
		List<MaterialStorage> res = service.statisticsStorage(storage);
		int count = service.countStorage(storage);
		
		PageResult pr = new PageResult();
		
		pr.setTotal(count);
		pr.setRows(res);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gson.toJson(pr);
	}
}
