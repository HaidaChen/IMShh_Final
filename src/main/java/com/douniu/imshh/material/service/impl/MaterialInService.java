package com.douniu.imshh.material.service.impl;

import java.util.List;

import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.dao.IMaterialInDao;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialIn;
import com.douniu.imshh.material.service.IMaterialInService;
import com.douniu.imshh.material.service.IMaterialService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class MaterialInService implements IMaterialInService{
	private IMaterialInDao dao;
	private IMaterialService materialService;
	
	@Override
	public List<MaterialIn> query(MaterialFilter filter) {
		MaterialFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"name", "category", "supplierName"});
		return dao.query(condition);
	}

	@Override
	public PageResult getPageResult(MaterialFilter filter) {
		PageResult pr = new PageResult();
		MaterialFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"name", "category", "supplierName"});
		pr.setRows(dao.getPageResult(condition));
		pr.setTotal(dao.count(condition));
		return pr;
	}
	
	@Override
	public MaterialIn getById(String id) {
		return dao.getById(id);
	}

	@Override
	public void inStorage(MaterialIn materialIn) {
		IDInjector.injector(materialIn);
		dao.insert(materialIn);
		materialService.addStorage(materialIn.getMaterialId(), materialIn.getAmount());
	}

	@Override
	public void batchInStorage(List<MaterialIn> materialIn) {
		IDInjector.injector(materialIn);
		dao.batchInsert(materialIn);
		for (MaterialIn item : materialIn){
			materialService.addStorage(item.getMaterialId(), item.getAmount());
		}
	}
	
	@Override
	public void delete(String id) {
		dao.delete(id);
	}

	public void setDao(IMaterialInDao dao) {
		this.dao = dao;
	}

	public void setMaterialService(IMaterialService materialService) {
		this.materialService = materialService;
	}
}
