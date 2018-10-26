package com.douniu.imshh.material.service;

import java.util.List;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.Inventory;

public interface IInventoryService {
	/**
	 * 根据查询条件（日期）晒选记录
	 * @param condition
	 * @return
	 */
	List<Inventory> query(Inventory condition);
	
	/**
	 * 根据查询条件（名称、供应商、分类、规格、分页条件）
	 * @param condition
	 * @return 包含盘点结果集，以及分页相关数据和关键指标的合计数据
	 */
	PageResult getPageResult(Inventory condition);	
	
	Inventory get(String id);
	
	/**
	 * 计价是指根据计价规则（1、最新价格，2、历史均价，3、历史最高，历史最低）临时计算出原材料的价格
	 * @param id
	 * @return
	 */
	List<Inventory> Pricing(String id);
	
	void newInventory();
	
	void delete(String id);
}
