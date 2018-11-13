package com.douniu.imshh.material.dao;

import java.util.List;

import com.douniu.imshh.material.domain.MaterialCategory;
import com.douniu.imshh.material.domain.MaterialFilter;

public interface IMaterialCategoryDao {
	List<MaterialCategory> getPageResult(MaterialFilter filter);
	
	List<MaterialCategory> query(MaterialFilter filter);
	
	int count(MaterialFilter filter);
	
	MaterialCategory getById(String id);

	void insert(MaterialCategory category);
	
	void update(MaterialCategory category);
	
	void delete(String id);
}
