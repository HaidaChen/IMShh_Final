package com.douniu.imshh.finance.reception.service;

import java.util.List;

import com.douniu.imshh.finance.reception.domain.Reception;

public interface IReceptionService {
	List<Reception> queryReception(Reception reception);
	int countReception(Reception reception);
	float getTotalDebt();
}
