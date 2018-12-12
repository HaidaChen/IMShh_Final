package com.douniu.imshh.material.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.common.ImportException;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.dao.IMaterialDao;
import com.douniu.imshh.material.domain.Category;
import com.douniu.imshh.material.domain.Material;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.service.ICategoryService;
import com.douniu.imshh.material.service.IMaterialService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class MaterialService implements IMaterialService{
	private IMaterialDao dao;
	private ICategoryService ctgService;	
	
	@Override
	public List<Material> query(MaterialFilter filter) {
		MaterialFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"name", "specification", "remark"});
		if (!StringUtils.isEmpty(condition.getCtgCode())){
			condition.setCtgCode(condition.getCtgCode() + "%");
		}
		return dao.query(condition);
	}

	@Override
	public PageResult getPageResult(MaterialFilter filter) {
		PageResult pr = new PageResult();
		MaterialFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"name", "specification", "remark"});
		if (!StringUtils.isEmpty(condition.getCtgCode())){
			condition.setCtgCode(condition.getCtgCode() + "%");
		}
		pr.setRows(dao.getPageResult(condition));
		pr.setTotal(dao.count(condition));
		return pr;
	}
	
	@Override
	public Material getById(String id) {
		Material material = dao.getById(id);
		return material;
	}

	@Override
	public boolean exist(MaterialFilter filter) {
		return dao.exactQuery(filter).size() > 0;
	}

	@Override
	public void newMaterial(Material material) {
		IDInjector.injector(material);
		dao.insert(material);
	}

	@Override
	public List<ImportException> checkImport(List<Material> materialList) {
		List<ImportException> exceptions = new ArrayList<ImportException>();
		String repeation = "";
		String categoryMapping = "";
		List<Material> fullMaterial = dao.query(new MaterialFilter());
		List<Category> fullCategory = ctgService.query(new MaterialFilter());
		
		for (int i = 0; i < materialList.size(); i++){
			Material material = materialList.get(i);
			if (fullMaterial.contains(material)){
				repeation += "," + (i+2);
			}
			
			if (!StringUtils.isEmpty(material.getCtg().getName())){
				Category category = new Category();
				category.setName(material.getCtg().getName());
				if (!fullCategory.contains(category)){
					categoryMapping += "," + (i+2);
				}
			}
			
		}
		if (!repeation.equals("")){
			exceptions.add(new ImportException("存在重复的原材料品类", "原材料重复的判断依据是，品名、规格1、规格2、规格3是否重复", repeation.substring(1), ""));
		}
		if (!categoryMapping.equals("")){
			exceptions.add(new ImportException("无法关联原材料类别", "类别名称与系统中已经存在的原材料分类不一致，导致无法关联", categoryMapping.substring(1), ""));			
		}
		return exceptions;
	}
	
	@Override
	public void importMaterial(List<Material> materialList) {
		List<Category> fullCategory = ctgService.query(new MaterialFilter());
		for (Material material : materialList){
			if (!material.getCtg().getCode().trim().equals("")){
				Category category = new Category();
				category.setCode(material.getCtg().getCode());
				int index = fullCategory.indexOf(category);
				Category match =  fullCategory.get(index);
				material.setCtg(match);
			}
		}
		IDInjector.injector(materialList);
		dao.batchInsert(materialList);
	}

	@Override
	public List<Material> exportMaterial(MaterialFilter filter) {
		return query(filter);
	}

	@Override
	public void updateMaterial(Material material) {
		dao.update(material);
	}

	@Override
	public void deleteMaterial(String id) {
		dao.delete(id);
	}

	@Override
	public void addStorage(String id, float storage) {
		Material tmaterial = dao.getById(id);
		float currentStorage = tmaterial.getStorage();
		tmaterial.setStorage(currentStorage + storage);
		dao.setStorage(tmaterial);
	}
	
	public void setDao(IMaterialDao dao) {
		this.dao = dao;
	}

	public void setCtgService(ICategoryService ctgService) {
		this.ctgService = ctgService;
	}

}
