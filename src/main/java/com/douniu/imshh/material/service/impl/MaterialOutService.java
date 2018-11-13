package com.douniu.imshh.material.service.impl;

import java.util.List;

import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.dao.IMaterialOutDao;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialOut;
import com.douniu.imshh.material.service.IMaterialOutService;
import com.douniu.imshh.material.service.IMaterialService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class MaterialOutService implements IMaterialOutService{
	private IMaterialOutDao dao;
	private IMaterialService materialService;
	
	@Override
	public List<MaterialOut> query(MaterialFilter filter) {
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
	public void outStorage(MaterialOut materialOut) {
		IDInjector.injector(materialOut);
		dao.insert(materialOut);
		materialService.addStorage(materialOut.getMaterialId(), 0-materialOut.getAmount());
	}

	@Override
	public void batchOutStorage(List<MaterialOut> materialOuts) {
		IDInjector.injector(materialOuts);
		dao.batchInsert(materialOuts);
		for (MaterialOut item : materialOuts){
			materialService.addStorage(item.getMaterialId(), 0-item.getAmount());
		}
	}

	@Override
	public void delete(String id) {
		dao.delete(id);
	}

	public void setDao(IMaterialOutDao dao) {
		this.dao = dao;
	}

	public void setMaterialService(IMaterialService materialService) {
		this.materialService = materialService;
	}

	
}
