package com.douniu.imshh.busdata.supplier.service;

import java.util.List;

import com.douniu.imshh.busdata.supplier.domain.Supplier;

public interface ISupplierService {
	List<Supplier> query(Supplier supplier);
	List<Supplier> queryNoPage(Supplier supplier);
	int count(Supplier supplier);
	Supplier getById(String id);
	void save(Supplier supplier);
	void delete(String id);
	void batchAdd(List<Supplier> suppliers);
}
