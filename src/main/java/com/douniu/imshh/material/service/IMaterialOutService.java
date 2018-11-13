package com.douniu.imshh.material.service;

import java.util.List;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialOut;

public interface IMaterialOutService {
	/**
	 * 根据查询条件（名称、分类、规格、日期）筛选记录
	 * @param materialOut
	 * @return
	 */
	List<MaterialOut> query(MaterialFilter filter);
	
	/**
	 * 根据查询条件（名称、供应商、分类、规格、日期、单价、总价）筛选记录
	 * @param condition
	 * @return 包含原材料出库明细结果集，以及分页相关数据和关键指标的合计数据
	 */
	PageResult getPageResult(MaterialFilter filter);
	
	void outStorage(MaterialOut materialOut);
	
	void batchOutStorage(List<MaterialOut> materialOuts);
	
	void delete(String id);
}
