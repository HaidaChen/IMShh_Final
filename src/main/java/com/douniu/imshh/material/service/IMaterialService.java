package com.douniu.imshh.material.service;

import java.util.List;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.Material;
import com.douniu.imshh.material.domain.MaterialFilter;

public interface IMaterialService {
	/**
	 * 根据查询条件（名称、供应商、分类、规格）晒选记录
	 * @param filter
	 * @return
	 */
	List<Material> query(MaterialFilter filter);
	
	/**
	 * 根据查询条件（名称、供应商、分类、规格、分页条件）
	 * @param filter
	 * @return 包含原材料结果集，以及分页相关数据和关键指标的合计数据
	 */
	PageResult getPageResult(MaterialFilter filter);
	
	Material getById(String id);
	
	void newMaterial(Material material);
	
	void batchAdd(List<Material> materialList);
	
	void updateMaterial(Material material);
	
	void deleteMaterial(String id);
	
}
