package com.douniu.imshh.finance.payment.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.finance.payment.domain.Payment;
import com.douniu.imshh.finance.payment.service.IPaymentService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/payment")
public class PaymentAction {
	@Autowired
	private IPaymentService service;
	
	@RequestMapping(value ="/loadpayment", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadPayment(Payment payment){
		List<Payment> res = service.queryPayment(payment);
		int count = service.countPayment(payment);
		
		PageResult pr = new PageResult();
		
		pr.setTotal(count);
		pr.setRows(res);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		return gson.toJson(pr);
	}
}
