package com.douniu.imshh.material.dao;

import java.util.List;

import com.douniu.imshh.material.domain.HistorySupplier;

public interface IHistorySupplierDao {
	List<HistorySupplier> getHistorySupplier(String materialId);
	
	HistorySupplier findById(String id);
	
	void insert(HistorySupplier supplier);
	
	void delete(String id);
}
