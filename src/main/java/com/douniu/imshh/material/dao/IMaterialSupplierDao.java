package com.douniu.imshh.material.dao;

import java.util.List;

import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialSupplier;

public interface IMaterialSupplierDao {
	List<MaterialSupplier> getPageResult(MaterialFilter filter);
	
	List<MaterialSupplier> query(MaterialFilter filter);
	
	int count(MaterialFilter filter);
	
	MaterialSupplier getById(String id);

	void insert(MaterialSupplier supplier);
	
	void update(MaterialSupplier supplier);
	
	void delete(String id);
}
