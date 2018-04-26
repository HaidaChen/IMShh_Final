package com.douniu.imshh.finance.account.service;

import java.util.List;

import com.douniu.imshh.finance.account.domain.Transaction;

public interface ITransactionService {
	List<Transaction> query(Transaction transaction);
	int count(Transaction transaction);
	List<Transaction> queryByOrderId(String orderId);
	List<Transaction> queryByPurchaseId(String purchaseId);
	Transaction getById(String id);
	void save(Transaction transaction);
}
