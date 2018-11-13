package com.douniu.imshh.material.dao;

import java.util.List;

import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialOut;

public interface IMaterialOutDao {
	List<MaterialOut> getPageResult(MaterialFilter filter);
	
	List<MaterialOut> query(MaterialFilter filter);
	
	int count(MaterialFilter filter);
	
	MaterialOut getById(String id);

	void insert(MaterialOut materialOut);
	
	void batchInsert(List<MaterialOut> materialOutList);
	
	void delete(String id);
}
