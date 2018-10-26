package com.douniu.imshh.material.service;

import java.util.List;

import com.douniu.imshh.material.domain.HistorySupplier;

public interface IHistorySupplierService {
	
	List<HistorySupplier> getHistorySupplier(String materialId);
	
	HistorySupplier getById(String id);
	
	void newSupplier(HistorySupplier supplier);
	
	void deleteSupplier(String id);
}
