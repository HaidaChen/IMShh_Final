package com.douniu.imshh.finance.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.Authorization;
import com.douniu.imshh.finance.domain.Account;
import com.douniu.imshh.finance.domain.FinanceFilter;
import com.douniu.imshh.finance.service.IAccountService;
import com.douniu.imshh.sys.service.IParameterService;
import com.douniu.imshh.utils.GsonUtil;
import com.douniu.imshh.utils.ImportAndExportUtil;
import com.douniu.imshh.utils.SheetData;

@Controller
@RequestMapping("/genLedger")
public class GeneralLedgerAction {
	@Autowired
	private IAccountService service;
	@Autowired
	private IParameterService pservice;
	
	@Authorization("0406")
	@RequestMapping(value ="/allBillPeriod", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String allBillPeriod(FinanceFilter filter){
		Map<String, String> period = pservice.getBillPeriod();
		return GsonUtil.toJson(period, null);
	}
	
	@Authorization("0406")
	@RequestMapping(value ="/getAccount", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getAccount(String billPeriod){
		List<Account> accounts = service.getGeneralLedger(billPeriod);
		return GsonUtil.toJson(accounts, null);
	}
	
	@Authorization("0406")
	@RequestMapping(value = "exportAccount", method = RequestMethod.GET)  
    @ResponseBody  
	public void exportAccount(HttpServletRequest request, HttpServletResponse response, String billPeriod){
		String periodDesc = billPeriod.substring(0, 4)+"年第"+billPeriod.substring(4)+"期";
		SheetData data = new SheetData("总账账簿_"+periodDesc);
		
		data.put("period", periodDesc);
		
		List<Account> accounts = service.getGeneralLedger(billPeriod);
		data.addDatas(accounts);
		ImportAndExportUtil.export("总账账簿.xls", data, request, response);
	}
}
