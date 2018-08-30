package com.douniu.imshh.finance.payment.dao;

import java.util.List;

import com.douniu.imshh.finance.payment.domain.Payment;

public interface IPaymentDao {
	List<Payment> queryPayment(Payment payment);
	int countPayment(Payment payment);
}
