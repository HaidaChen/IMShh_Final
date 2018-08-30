package com.douniu.imshh.finance.dashboard.dao;

import java.util.List;

import com.douniu.imshh.finance.dashboard.domain.ProductIndicators;
import com.douniu.imshh.finance.dashboard.domain.ProductStatistics;

public interface IProductViewDao {
	/** 安查询条件统计产品视图  **/
	List<ProductStatistics> queryProduct(ProductStatistics condition);
	/** 产品视图记录数 **/
	int countProduct(ProductStatistics condition);
	/** 获取产品重要指标 **/
	ProductIndicators getIndicators(String year);
}
