package com.douniu.imshh.material.service;

import java.util.List;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.Category;
import com.douniu.imshh.material.domain.MaterialFilter;

public interface ICategoryService {
	List<Category> query(MaterialFilter filter);
	PageResult getPageResult(MaterialFilter filter);
	Category getById(String id);
	void newCategory(Category category);
	void updateCategory(Category category);
	void deleteCategory(String id);
}
