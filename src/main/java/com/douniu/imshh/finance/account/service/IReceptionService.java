package com.douniu.imshh.finance.account.service;

import java.util.List;

import com.douniu.imshh.finance.account.domain.Reception;
import com.douniu.imshh.finance.account.domain.Transaction;
import com.douniu.imshh.finance.storage.domain.Deliver;

public interface IReceptionService {
	Reception statistics(String year);
	List<Reception> statisticsByCustomer(String customerName);
	void addReception(Deliver deliver);
	void addPayment(Transaction transaction);
}
