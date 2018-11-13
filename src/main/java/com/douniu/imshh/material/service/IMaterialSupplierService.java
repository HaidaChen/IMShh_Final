package com.douniu.imshh.material.service;

import java.util.List;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialSupplier;

public interface IMaterialSupplierService {
	List<MaterialSupplier> query(MaterialFilter filter);
	PageResult getPageResult(MaterialFilter filter);
	MaterialSupplier getById(String id);
	void newSupplier(MaterialSupplier supplier);
	void updateSupplier(MaterialSupplier supplier);
	void deleteSupplier(String id);
}
