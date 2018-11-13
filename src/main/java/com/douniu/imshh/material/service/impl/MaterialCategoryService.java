package com.douniu.imshh.material.service.impl;

import java.util.List;

import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.dao.IMaterialCategoryDao;
import com.douniu.imshh.material.domain.MaterialCategory;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.service.IMaterialCategoryService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class MaterialCategoryService implements IMaterialCategoryService{
	private IMaterialCategoryDao dao;
	
	@Override
	public List<MaterialCategory> query(MaterialFilter filter) {
		MaterialFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"name"});
		return dao.query(condition);
	}

	@Override
	public PageResult getPageResult(MaterialFilter filter) {
		PageResult pr = new PageResult();
		MaterialFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"name"});
		pr.setRows(dao.getPageResult(condition));
		pr.setTotal(dao.count(condition));
		return pr;
	}

	@Override
	public MaterialCategory getById(String id) {
		return dao.getById(id);
	}

	@Override
	public void newCategory(MaterialCategory category) {
		IDInjector.injector(category);
		dao.insert(category);
	}

	@Override
	public void updateCategory(MaterialCategory category) {
		dao.update(category);
	}

	@Override
	public void deleteCategory(String id) {
		dao.delete(id);
	}

	public void setDao(IMaterialCategoryDao dao) {
		this.dao = dao;
	}

	
}
