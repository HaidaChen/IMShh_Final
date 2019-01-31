package com.douniu.imshh.supplier.service;

import java.util.List;

import com.douniu.imshh.common.ImportException;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.supplier.domain.Supplier;
import com.douniu.imshh.supplier.domain.SupplierFilter;

public interface ISupplierService {
	PageResult getPageResult(SupplierFilter filter);
	List<Supplier> query(SupplierFilter filter);
	Supplier getById(String id);
	void newSupplier(Supplier supplier);
	void updateSupplier(Supplier supplier);
	void delete(String id);
	List<ImportException> checkImport(List<Supplier> suppliers);
	void importSupplier(List<Supplier> suppliers);
	//void batchAdd(List<Supplier> suppliers);
}
