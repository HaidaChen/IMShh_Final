package com.douniu.imshh.material.service;

import java.util.List;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.MaterialCategory;
import com.douniu.imshh.material.domain.MaterialFilter;

public interface IMaterialCategoryService {
	List<MaterialCategory> query(MaterialFilter filter);
	PageResult getPageResult(MaterialFilter filter);
	MaterialCategory getById(String id);
	void newCategory(MaterialCategory category);
	void updateCategory(MaterialCategory category);
	void deleteCategory(String id);
}
