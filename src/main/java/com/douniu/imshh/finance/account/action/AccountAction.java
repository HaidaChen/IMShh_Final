package com.douniu.imshh.finance.account.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.finance.account.domain.Account;
import com.douniu.imshh.finance.account.service.IAccountService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/account")
public class AccountAction {
	
	@Autowired
	private IAccountService service;
	
	@RequestMapping(value ="/query", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String query(String type){
		List<Account> accounts = service.queryByType(type);
		Gson gson = new Gson();
        return gson.toJson(accounts);
	}
	
	@RequestMapping(value ="/save", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String save(Account account){
		service.save(account);
        return account.getId();
	}
	
	@RequestMapping(value ="/delete", produces = "application/json; charset=utf-8")
	@ResponseBody
	public void delete(String id){
		service.delete(id);
	}
}
