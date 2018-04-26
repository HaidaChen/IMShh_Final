package com.douniu.imshh.finance.account.service.impl;

import java.util.List;

import com.douniu.imshh.finance.account.dao.ITransactionDao;
import com.douniu.imshh.finance.account.domain.Transaction;
import com.douniu.imshh.finance.account.service.ITransactionService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class TransactionService implements ITransactionService{
	private ITransactionDao dao;
	
	@Override
	public List<Transaction> query(Transaction transaction) {
		Transaction condition = LikeFlagUtil.appendLikeFlag(transaction, new String[]{"tranUser", "orderId", "purchaseId"});
		return dao.query(condition);
	}

	@Override
	public int count(Transaction transaction) {
		Transaction condition = LikeFlagUtil.appendLikeFlag(transaction, new String[]{"tranUser", "orderId", "purchaseId"});
		return dao.count(condition);
	}

	@Override
	public List<Transaction> queryByOrderId(String orderId) {
		return dao.findByOrderId(orderId);
	}

	@Override
	public List<Transaction> queryByPurchaseId(String purchaseId) {
		return dao.findByPurchaseId(purchaseId);
	}

	@Override
	public Transaction getById(String id) {
		return dao.findById(id);
	}

	@Override
	public void save(Transaction transaction) {
		if (transaction.getId().equals("")){
			transaction.setId(System.currentTimeMillis()+"");
			transaction.setStatus(1);
			dao.insert(transaction);
		}else{
			dao.update(transaction);
		}
	}

	public void setDao(ITransactionDao dao) {
		this.dao = dao;
	}

	
}
