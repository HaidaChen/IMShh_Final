package com.douniu.imshh.finance.reception.dao;

import java.util.List;

import com.douniu.imshh.finance.reception.domain.Reception;

public interface IReceptionDao {
	List<Reception> query(Reception reception);
	List<Reception> queryNoPage(Reception reception);
	int count(Reception reception);
	List<Reception> queryBySupplier(Reception reception);
	int countBySupplier(Reception reception);
	Reception findById(String id);
	void insert(Reception reception);
	void batchInsert(List<Reception> receptions);
	void update(Reception reception);
	void deleteById(String id);
}
