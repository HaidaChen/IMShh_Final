package com.douniu.imshh.supplier.dao;

import java.util.List;

import com.douniu.imshh.supplier.domain.Supplier;
import com.douniu.imshh.supplier.domain.SupplierFilter;

public interface ISupplierDao {
	List<Supplier> getPageResult(SupplierFilter supplier);
	List<Supplier> query(SupplierFilter supplier);
	int count(SupplierFilter supplier);
	boolean exist(SupplierFilter filter);
	Supplier findById(String id);
	void insert(Supplier supplier);
	void batchInsert(List<Supplier> suppliers);
	void update(Supplier supplier);
	void deleteById(String id);
}
