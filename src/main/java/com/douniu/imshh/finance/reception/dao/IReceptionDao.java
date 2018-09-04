package com.douniu.imshh.finance.reception.dao;

import java.util.List;

import com.douniu.imshh.finance.reception.domain.Reception;

public interface IReceptionDao {
	List<Reception> queryReception(Reception reception);
	int countReception(Reception reception);
	float getTotalDebt();
}
