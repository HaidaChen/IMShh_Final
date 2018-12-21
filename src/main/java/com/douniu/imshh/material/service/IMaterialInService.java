package com.douniu.imshh.material.service;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialInBill;

public interface IMaterialInService {
	PageResult getPageResult(MaterialFilter filter);
	MaterialInBill getById(String id);
	void newMaterialIn(MaterialInBill materialIn);
	void updateMaterialIn(MaterialInBill materialIn);
	void deleteMaterialIn(String id);
	
	/**
	 * 根据查询条件（名称、供应商、分类、规格、日期、单价、总价）筛选记录
	 * @param materialIn
	 * @return
	 *//*
	List<MaterialIn> query(MaterialFilter filter);
	
	*//**
	 * 根据查询条件（名称、供应商、分类、规格、日期、单价、总价）筛选记录
	 * @param condition
	 * @return 包含原材料人库明细结果集，以及分页相关数据
	 *//*
	PageResult getPageResult(MaterialFilter filter);
		
	MaterialIn getById(String id);
	
	void inStorage(MaterialIn materialIn);
	
	void batchInStorage(List<MaterialIn> materialIn);	
	
	void importMaterialIn(List<MaterialIn> materialInList);
	
	List<ImportException> checkImport(List<MaterialIn> materialInList);
	
	List<MaterialIn> exportMaterialIn(MaterialFilter filter);*/
}
