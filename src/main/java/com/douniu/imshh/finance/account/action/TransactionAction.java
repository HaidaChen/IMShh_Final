package com.douniu.imshh.finance.account.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.finance.account.domain.Account;
import com.douniu.imshh.finance.account.domain.Transaction;
import com.douniu.imshh.finance.account.service.IAccountService;
import com.douniu.imshh.finance.account.service.ITransactionService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/transaction")
public class TransactionAction {
	
	@Autowired
	private ITransactionService service;
	@Autowired
	private IAccountService accountService;
	
	@RequestMapping("/main")
    public ModelAndView enter(String accountId, String accountNo, String bank){
        ModelAndView mav = new ModelAndView();
        String maskNo = accountNo.substring(0, 4) + "****" + accountNo.substring(accountNo.length() - 4);
        mav.addObject("accountId", accountId);
        mav.addObject("bank", bank);
        mav.addObject("maskNo", maskNo);
        mav.setViewName("/finance/account/transaction");
        return mav;
    }
	
	@RequestMapping("/edit")
	public ModelAndView edit(Transaction transaction){
		ModelAndView mav = new ModelAndView();
		if (transaction.getId() != null && transaction.getId() != ""){
			Transaction oTransaction = service.getById(transaction.getId());
			mav.addObject("transaction", oTransaction);
		}else{
			mav.addObject("transaction", transaction);
		}
        mav.setViewName("/finance/account/edit");
        return mav;
	}
	
	@RequestMapping("/save")
	public ModelAndView save(Transaction transaction){
		service.save(transaction);
		Account account = accountService.findById(transaction.getAccountId());
        return enter(transaction.getAccountId(), account.getAccountNo(), account.getBank());
	}
	
	@RequestMapping(value ="/loadtransaction", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadInvoice(Transaction transaction){
		List<Transaction> res = service.query(transaction);
		int count = service.count(transaction);
		
		PageResult pr = new PageResult();
		
		pr.setTotal(count);
		pr.setRows(res);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
		return gson.toJson(pr);
	}
	
	@RequestMapping(value ="/findbyorder", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String findByOrder(String orderIdentify){
		List<Transaction> res = service.queryByOrder(orderIdentify);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
		return gson.toJson(res);
	}
	
}
