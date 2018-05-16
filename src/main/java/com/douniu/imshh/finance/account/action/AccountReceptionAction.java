package com.douniu.imshh.finance.account.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.finance.account.domain.Reception;
import com.douniu.imshh.finance.account.service.IReceptionService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/accountrpt")
public class AccountReceptionAction {
	@Autowired
	private IReceptionService serivce;
	
	@RequestMapping(value ="/statistics", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String statistics(String year){
		Reception reception = serivce.statistics(year);
		Gson gson = new Gson();
        return gson.toJson(reception);
	}
	
	@RequestMapping(value ="/statisticsByCustomer", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String statisticsByCustomer(String year){
		List<Reception> receptions = serivce.statisticsByCustomer(year);
		Gson gson = new Gson();
		String str = gson.toJson(receptions);
		System.out.println(str);
        return gson.toJson(receptions);
	}
}
