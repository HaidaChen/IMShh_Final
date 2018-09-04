package com.douniu.imshh.finance.reception.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.finance.reception.domain.Reception;
import com.douniu.imshh.finance.reception.service.IReceptionService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/reception")
public class ReceptionAction {
	@Autowired
	private IReceptionService serivce;
	
	@RequestMapping(value ="/loadreception", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadReception(Reception reception){
		List<Reception> res = serivce.queryReception(reception);
		int count = serivce.countReception(reception);
		
		PageResult pr = new PageResult();
		
		pr.setTotal(count);
		pr.setRows(res);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gson.toJson(pr);
	}
	
	@RequestMapping(value ="/gettotaldebt", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getTotalDebt(){
		return serivce.getTotalDebt() + "";
	}
}
