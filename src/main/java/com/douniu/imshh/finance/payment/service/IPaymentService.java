package com.douniu.imshh.finance.payment.service;

import java.util.List;

import com.douniu.imshh.finance.payment.domain.Payment;

public interface IPaymentService {
	List<Payment> queryPayment(Payment payment);
	int countPayment(Payment payment);
	float getTotalDebt();
}
