package com.douniu.imshh.finance.account.service;

import java.util.List;

import com.douniu.imshh.finance.account.domain.Payment;
import com.douniu.imshh.finance.account.domain.Transaction;
import com.douniu.imshh.finance.reception.domain.Reception;

public interface IPaymentService {
	Payment statistics(String year);
	List<Payment> statisticsBySupplier(String year);
	void addDebt(Reception reception);
	void addPayment(Transaction transaction);
}
