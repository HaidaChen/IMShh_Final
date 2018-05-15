package com.douniu.imshh.finance.account.dao;

import java.util.List;

import com.douniu.imshh.finance.account.domain.Account;

public interface IAccountDao {
	List<Account> queryByType(String type);
	List<Account> findThird();
	Account findById(String id);
	Account findByNo(String no);
	void insert(Account account);
	void update(Account account);
	void deleteById(String id);
}
