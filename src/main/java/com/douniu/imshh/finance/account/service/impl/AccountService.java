package com.douniu.imshh.finance.account.service.impl;

import java.util.List;

import com.douniu.imshh.finance.account.dao.IAccountDao;
import com.douniu.imshh.finance.account.domain.Account;
import com.douniu.imshh.finance.account.service.IAccountService;

public class AccountService implements IAccountService{

	private IAccountDao dao;
	
	@Override
	public List<Account> queryByType(String type) {
		// TODO Auto-generated method stub
		return dao.queryByType(type);
	}

	@Override
	public Account findById(String id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	public void save(Account account) {
		// TODO Auto-generated method stub
		if (account.getId() == null || account.getId().equals("")){
			account.setId(System.currentTimeMillis()+"");
			account.setStatus(1);
			dao.insert(account);
		}else{
			dao.update(account);
		}
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		dao.deleteById(id);
	}

	public void setDao(IAccountDao dao) {
		this.dao = dao;
	}

	
}
