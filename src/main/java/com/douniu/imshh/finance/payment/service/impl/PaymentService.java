package com.douniu.imshh.finance.payment.service.impl;

import java.util.List;

import com.douniu.imshh.finance.payment.dao.IPaymentDao;
import com.douniu.imshh.finance.payment.domain.Payment;
import com.douniu.imshh.finance.payment.service.IPaymentService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class PaymentService implements IPaymentService{
	private IPaymentDao dao;

	@Override
	public List<Payment> queryPayment(Payment payment) {
		Payment condition = LikeFlagUtil.appendLikeFlag(payment, new String[]{"supplierName"});
		return dao.queryPayment(condition);
	}

	@Override
	public int countPayment(Payment payment) {
		Payment condition = LikeFlagUtil.appendLikeFlag(payment, new String[]{"supplierName"});
		return dao.countPayment(condition);
	}
	
	@Override
	public float getTotalDebt() {
		return dao.getTotalDebt();
	}

	public void setDao(IPaymentDao dao) {
		this.dao = dao;
	}

}
