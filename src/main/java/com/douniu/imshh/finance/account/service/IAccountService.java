package com.douniu.imshh.finance.account.service;

import java.util.List;

import com.douniu.imshh.finance.account.domain.Account;

public interface IAccountService {
	List<Account> queryByType(String type);
	Account findById(String id);
	void save(Account account);
	void delete(String id);
}
