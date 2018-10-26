package com.douniu.imshh.material.service;

import java.util.List;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.Material;
import com.douniu.imshh.material.domain.MaterialIn;

public interface IMaterialInService {
	/**
	 * 根据查询条件（名称、供应商、分类、规格、日期、单价、总价）筛选记录
	 * @param materialIn
	 * @return
	 */
	List<MaterialIn> query(MaterialIn condition);
	
	/**
	 * 根据查询条件（名称、供应商、分类、规格、日期、单价、总价）筛选记录
	 * @param condition
	 * @return 包含原材料人库明细结果集，以及分页相关数据
	 */
	PageResult getPageResult(MaterialIn condition);
	
	/**
	 * 获得单个原材料的入库明细
	 * @param material
	 * @return
	 */
	List<MaterialIn> query(Material material);
	
	void inStorage(MaterialIn materialIn);
	
	void update(MaterialIn materialIn);
	
	void delete(MaterialIn materialIn);
}
