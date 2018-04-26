package com.douniu.imshh.busdata.supplier.dao;

import java.util.List;

import com.douniu.imshh.busdata.supplier.domain.Supplier;

public interface ISupplierDao {
	List<Supplier> query(Supplier supplier);
	List<Supplier> queryNoPage(Supplier supplier);
	int count(Supplier supplier);
	Supplier findById(String id);
	void insert(Supplier supplier);
	void batchInsert(List<Supplier> suppliers);
	void update(Supplier supplier);
	void deleteById(String id);
}
