package com.douniu.imshh.finance.account.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.douniu.imshh.finance.account.dao.IPaymentDao;
import com.douniu.imshh.finance.account.domain.Payment;
import com.douniu.imshh.finance.account.domain.Transaction;
import com.douniu.imshh.finance.account.service.IPaymentService;
import com.douniu.imshh.finance.reception.domain.Reception;

public class PaymentService implements IPaymentService{

	private IPaymentDao dao;
	
	@Override
	public Payment statistics(String year) {
		return dao.statistics(year);
	}

	@Override
	public List<Payment> statisticsBySupplier(String supplierName) {
		return dao.statisticsBySupplier("%"+supplierName+"%");
	}

	@Override
	public void addDebt(Reception reception) {
		DateFormat format = new SimpleDateFormat("yyyy-MM");  
        String month = format.format(reception.getReceiveDate());  
        
		Payment payment = new Payment();
		payment.setSupplierId(reception.getSupplierId());
		payment.setSupplierName(reception.getSupplierName());
		payment.setMonth(month);
		payment.setDebt(reception.getTotlemnt());
		dao.addDebt(payment);
	}

	@Override
	public void addPayment(Transaction transaction) {
		DateFormat format = new SimpleDateFormat("yyyy-MM");  
        String month = format.format(transaction.getTranDate());  
		
        Payment payment = new Payment();
		payment.setSupplierName(transaction.getTranUser());
		payment.setMonth(month);
		payment.setPayment(Math.abs(transaction.getTranAmount()));
		dao.addPayment(payment);
	}

	public void setDao(IPaymentDao dao) {
		this.dao = dao;
	}
	
}
