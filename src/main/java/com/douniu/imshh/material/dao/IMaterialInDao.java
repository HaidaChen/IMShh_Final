package com.douniu.imshh.material.dao;

import java.util.List;

import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialIn;

public interface IMaterialInDao {
	List<MaterialIn> getPageResult(MaterialFilter filter);
	
	List<MaterialIn> query(MaterialFilter filter);
	
	int count(MaterialFilter filter);
	
	MaterialIn getById(String id);

	void insert(MaterialIn materialIn);
	
	void batchInsert(List<MaterialIn> materialInList);
	
	void delete(String id);
}
