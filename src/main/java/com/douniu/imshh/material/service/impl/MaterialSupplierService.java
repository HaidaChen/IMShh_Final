package com.douniu.imshh.material.service.impl;

import java.util.List;

import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.dao.IMaterialSupplierDao;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialSupplier;
import com.douniu.imshh.material.service.IMaterialSupplierService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class MaterialSupplierService implements IMaterialSupplierService{
	private IMaterialSupplierDao dao;
	
	@Override
	public List<MaterialSupplier> query(MaterialFilter filter) {
		MaterialFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"name", "category"});
		return dao.query(condition);
	}

	@Override
	public PageResult getPageResult(MaterialFilter filter) {
		PageResult pr = new PageResult();
		MaterialFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"name", "category"});
		pr.setRows(dao.getPageResult(condition));
		pr.setTotal(dao.count(condition));
		return pr;
	}

	@Override
	public MaterialSupplier getById(String id) {
		return dao.getById(id);
	}

	@Override
	public void newSupplier(MaterialSupplier supplier) {
		IDInjector.injector(supplier);
		dao.insert(supplier);
	}

	@Override
	public void updateSupplier(MaterialSupplier supplier) {
		dao.update(supplier);
	}

	@Override
	public void deleteSupplier(String id) {
		dao.delete(id);
	}

	public void setDao(IMaterialSupplierDao dao) {
		this.dao = dao;
	}

}
