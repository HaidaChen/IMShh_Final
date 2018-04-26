package com.douniu.imshh.finance.account.dao;

import java.util.List;

import com.douniu.imshh.finance.account.domain.Transaction;

public interface ITransactionDao {
	List<Transaction> query(Transaction transaction);
	int count(Transaction transaction);
	Transaction findById(String id);
	List<Transaction> findByOrderId(String orderId);
	List<Transaction> findByPurchaseId(String purchaseId);
	void insert(Transaction transaction);
	void update(Transaction Transaction);
}
