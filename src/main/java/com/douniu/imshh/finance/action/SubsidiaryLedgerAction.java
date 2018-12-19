package com.douniu.imshh.finance.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.finance.domain.Account;
import com.douniu.imshh.finance.domain.FinanceFilter;
import com.douniu.imshh.finance.service.IAccountService;
import com.douniu.imshh.sys.service.IParameterService;
import com.douniu.imshh.utils.GsonUtil;

@Controller
@RequestMapping("/subLedger")
public class SubsidiaryLedgerAction {
	@Autowired
	private IAccountService service;
	@Autowired
	private IParameterService pservice;
	
	@RequestMapping(value ="/allBillPeriod", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String allBillPeriod(FinanceFilter filter){
		Map<String, String> period = pservice.getDictionary("bill.account.period");
		return GsonUtil.toJson(period, null);
	}
	
	@RequestMapping(value ="/getAccount", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getAccount(String subjectId, String billPeriod){
		List<Account> accounts = service.getByPeriod(subjectId, billPeriod);
		return GsonUtil.toJson(accounts, null);
	}
}
