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
	
	@RequestMapping(value ="/save", produces = "application/json; charset=utf-8")
	@ResponseBody
	public int save(Transaction transaction){
		Account account = accountService.findByNo(transaction.getTranAccountNo());
		if (account == null){
			account = new Account();
			account.setAccountNo(transaction.getTranAccountNo());
			account.setAccountUser(transaction.getTranUser());
			account.setAccountType("3");
			account.setBank(transaction.getTranBank());
			accountService.save(account);
		}
		service.save(transaction);
		return 1;
	}
	
	@RequestMapping(value ="/loadtransaction", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadtransaction(Transaction transaction){
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
