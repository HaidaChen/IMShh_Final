package com.douniu.imshh.material.service.impl;

import java.util.List;

import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.dao.IMaterialDao;
import com.douniu.imshh.material.domain.Material;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.service.IHistorySupplierService;
import com.douniu.imshh.material.service.IMaterialService;

public class MaterialService implements IMaterialService{
	private IMaterialDao dao;
	private IHistorySupplierService supplierService;
	
	@Override
	public List<Material> query(MaterialFilter filter) {
		return dao.query(filter);
	}

	@Override
	public PageResult getPageResult(MaterialFilter filter) {
		PageResult pr = new PageResult();
		pr.setRows(dao.getPageResult(filter));
		pr.setTotal(dao.count(filter));
		return pr;
	}
	
	@Override
	public Material getById(String id) {
		Material material = dao.getById(id);
		material.setHisSuppliers(supplierService.getHistorySupplier(id));
		return material;
	}

	@Override
	public void newMaterial(Material material) {
		IDInjector.injector(material);
		dao.insert(material);
	}

	@Override
	public void batchAdd(List<Material> materialList) {
		IDInjector.injector(materialList);
		dao.batchInsert(materialList);
	}

	@Override
	public void updateMaterial(Material material) {
		dao.update(material);
	}

	@Override
	public void deleteMaterial(String id) {
		dao.delete(id);
	}

	public void setDao(IMaterialDao dao) {
		this.dao = dao;
	}

	public void setSupplierService(IHistorySupplierService supplierService) {
		this.supplierService = supplierService;
	}	
}
