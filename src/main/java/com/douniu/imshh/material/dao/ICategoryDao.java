package com.douniu.imshh.material.dao;

import java.util.List;

import com.douniu.imshh.material.domain.Category;
import com.douniu.imshh.material.domain.MaterialFilter;

public interface ICategoryDao {
	List<Category> getPageResult(MaterialFilter filter);
	
	List<Category> query(MaterialFilter filter);
	
	int count(MaterialFilter filter);
	
	Category getById(String id);
	
	Category getByCode(String code);

	void insert(Category category);
	
	void update(Category category);
	
	void delete(String id);
}
