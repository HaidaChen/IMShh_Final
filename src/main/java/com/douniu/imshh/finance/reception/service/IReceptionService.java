package com.douniu.imshh.finance.reception.service;

import java.util.List;

import com.douniu.imshh.finance.reception.domain.Reception;

public interface IReceptionService {
	List<Reception> query(Reception reception);
	List<Reception> queryNoPage(Reception reception);
	int count(Reception reception);
	Reception getById(String id);
	void save(Reception reception);
	void delete(String id);
	void batchAdd(List<Reception> receptions);
}
