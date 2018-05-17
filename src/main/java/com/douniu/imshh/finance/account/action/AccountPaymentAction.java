package com.douniu.imshh.finance.account.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.finance.account.domain.Payment;
import com.douniu.imshh.finance.account.service.IPaymentService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/accountpmt")
public class AccountPaymentAction {
	@Autowired
	private IPaymentService serivce;
	
	@RequestMapping(value ="/statistics", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String statistics(String year){
		Payment payment = serivce.statistics(year);
		Gson gson = new Gson();
        return gson.toJson(payment);
	}
	
	@RequestMapping(value ="/statisticsBySupplier", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String statisticsBySupplier(String supplierName){
		List<Payment> payments = serivce.statisticsBySupplier(supplierName);
		
		PageResult pr = new PageResult();
		pr.setTotal(payments.size());
		pr.setRows(payments);		
		
		Gson gson = new Gson();
        return gson.toJson(pr);
	}
}
