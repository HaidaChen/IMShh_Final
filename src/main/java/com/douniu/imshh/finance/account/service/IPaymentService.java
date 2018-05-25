package com.douniu.imshh.finance.account.service;

import java.util.List;

import com.douniu.imshh.finance.account.domain.Payment;
import com.douniu.imshh.finance.account.domain.Transaction;
import com.douniu.imshh.finance.storage.domain.MaterialIn;

public interface IPaymentService {
	Payment statistics(String year);
	List<Payment> statisticsBySupplier(String supplierName);
	void addDebt(MaterialIn reception);
	void addPayment(Transaction transaction);
}
