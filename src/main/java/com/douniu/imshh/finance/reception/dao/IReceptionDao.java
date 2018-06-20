package com.douniu.imshh.finance.reception.dao;

import java.util.List;

import com.douniu.imshh.finance.reception.domain.Reception;

public interface IReceptionDao {
	Reception statistics(Reception reception);
	List<Reception> statisticsByOrder(Reception reception);
	void addReception(Reception reception);
	void addPayment(Reception reception);
}
