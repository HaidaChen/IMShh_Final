package com.douniu.imshh.material.service.impl;

import java.util.List;

import org.springframework.util.StringUtils;

import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.dao.ICategoryDao;
import com.douniu.imshh.material.domain.Category;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.service.ICategoryService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class CategoryService implements ICategoryService{
	private ICategoryDao dao;
	
	@Override
	public List<Category> query(MaterialFilter filter) {
		MaterialFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"name", "remark"});
		if (!StringUtils.isEmpty(condition.getCtgCode())){
			condition.setCtgCode(condition.getCtgCode() + "%");
		}
		return dao.query(condition);
	}

	@Override
	public PageResult getPageResult(MaterialFilter filter) {
		PageResult pr = new PageResult();
		MaterialFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"name", "remark"});
		if (!StringUtils.isEmpty(condition.getCtgCode())){
			condition.setCtgCode(condition.getCtgCode() + "%");
		}
		pr.setRows(dao.getPageResult(condition));
		pr.setTotal(dao.count(condition));
		return pr;
	}

	@Override
	public Category getById(String id) {
		return dao.getById(id);
	}

	@Override
	public Category getByCode(String code) {
		return dao.getByCode(code);
	}

	@Override
	public void newCategory(Category category) {
		IDInjector.injector(category);
		dao.insert(category);
	}

	@Override
	public void updateCategory(Category category) {
		dao.update(category);
	}

	@Override
	public void deleteCategory(String id) {
		dao.delete(id);
	}

	public void setDao(ICategoryDao dao) {
		this.dao = dao;
	}

	
}
