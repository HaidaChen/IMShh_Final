package com.douniu.imshh.finance.account.dao;

import java.util.List;

import com.douniu.imshh.finance.account.domain.Reception;

public interface IReceptionDao {
	Reception statistics(String year);
	List<Reception> statisticsByCustomer();
	void addReception(Reception reception);
	void addPayment(Reception reception);
}
