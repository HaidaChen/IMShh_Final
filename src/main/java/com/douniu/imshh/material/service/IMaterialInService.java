package com.douniu.imshh.material.service;

import java.util.List;

import com.douniu.imshh.common.ImportException;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialIn;

public interface IMaterialInService {
	/**
	 * 根据查询条件（名称、供应商、分类、规格、日期、单价、总价）筛选记录
	 * @param materialIn
	 * @return
	 */
	List<MaterialIn> query(MaterialFilter filter);
	
	/**
	 * 根据查询条件（名称、供应商、分类、规格、日期、单价、总价）筛选记录
	 * @param condition
	 * @return 包含原材料人库明细结果集，以及分页相关数据
	 */
	PageResult getPageResult(MaterialFilter filter);
		
	MaterialIn getById(String id);
	
	void inStorage(MaterialIn materialIn);
	
	void batchInStorage(List<MaterialIn> materialIn);	
	
	void importMaterialIn(List<MaterialIn> materialInList);
	
	List<ImportException> checkImport(List<MaterialIn> materialInList);
	
	List<MaterialIn> exportMaterialIn(MaterialFilter filter);
}
