package com.douniu.imshh.finance.account.dao;

import java.util.List;

import com.douniu.imshh.finance.account.domain.Payment;

public interface IPaymentDao {
	Payment statistics(String year);
	List<Payment> statisticsBySupplier(String supplierName);
	void addDebt(Payment payment);
	void addPayment(Payment payment);
}
