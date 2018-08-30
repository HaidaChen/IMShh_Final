package com.douniu.imshh.finance.dashboard.dao;

import java.util.List;

import com.douniu.imshh.finance.dashboard.domain.MaterialIndicators;
import com.douniu.imshh.finance.dashboard.domain.MaterialStatistics;

public interface IMaterialViewDao {
	/** 安查询条件统计原材料视图  **/
	List<MaterialStatistics> queryMaterial(MaterialStatistics condition);
	/** 原材料视图记录数 **/
	int countMaterial(MaterialStatistics condition);
	/** 获取原材料重要指标 **/
	MaterialIndicators getIndicators(String year);
}
