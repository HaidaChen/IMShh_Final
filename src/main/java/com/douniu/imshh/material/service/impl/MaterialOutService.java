package com.douniu.imshh.material.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.common.ImportException;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.dao.IMaterialOutDao;
import com.douniu.imshh.material.domain.Material;
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
	public MaterialOut getById(String id) {
		return dao.getById(id);
	}

	@Override
	public void importMaterialOut(List<MaterialOut> materialOutList) {
		List<Material> fullMaterial = materialService.query(new MaterialFilter());
		for (MaterialOut item : materialOutList){
			Material mtl = new Material();
			mtl.setName(item.getMaterialName());
			mtl.setSpecification1(item.getSpecification1());
			mtl.setSpecification2(item.getSpecification2());
			mtl.setSpecification3(item.getSpecification3());
			int mi = fullMaterial.indexOf(mtl);
			item.setMaterialId(fullMaterial.get(mi).getId());
		}
		batchOutStorage(materialOutList);
	}

	@Override
	public List<ImportException> checkImport(List<MaterialOut> materialOutList) {
		List<ImportException> exceptions = new ArrayList<ImportException>();
		String unassociation_material = "";
		List<Material> fullMaterials = materialService.query(new MaterialFilter());
		
		for (int i = 0; i < materialOutList.size(); i++){
			MaterialOut materialout = materialOutList.get(i);
			
			Material material = new Material();
			material.setName(materialout.getMaterialName());
			material.setSpecification1(materialout.getSpecification1());
			material.setSpecification2(materialout.getSpecification2());
			material.setSpecification3(materialout.getSpecification3());
			if (!fullMaterials.contains(material)){
				unassociation_material += "," + (i+2);
			}
		}
		if (!unassociation_material.equals("")){
			exceptions.add(new ImportException("无法关联原材料", "原材料名称、规格1、规格2、规则3；与系统中已经存在的原材料品类不匹配，导致无法关联", unassociation_material.substring(1), ""));
		}
		return exceptions;
	}

	@Override
	public List<MaterialOut> exportMaterialOut(MaterialFilter filter) {
		return query(filter);
	}

	public void setDao(IMaterialOutDao dao) {
		this.dao = dao;
	}

	public void setMaterialService(IMaterialService materialService) {
		this.materialService = materialService;
	}
	
}
