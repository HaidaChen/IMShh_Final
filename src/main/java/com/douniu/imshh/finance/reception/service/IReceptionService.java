package com.douniu.imshh.finance.reception.service;

import java.util.List;

import com.douniu.imshh.finance.account.domain.Transaction;
import com.douniu.imshh.finance.reception.domain.Reception;
import com.douniu.imshh.finance.storage.domain.ProductOut;

public interface IReceptionService {
	Reception statistics(Reception reception);
	List<Reception> statisticsByOrder(Reception reception);
	void addReception(ProductOut deliver);
	void addPayment(Transaction transaction);
}
