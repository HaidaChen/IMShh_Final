package com.douniu.imshh.finance.dashboard.service;

import java.util.List;

import com.douniu.imshh.finance.dashboard.domain.MaterialIndicators;
import com.douniu.imshh.finance.dashboard.domain.MaterialStatistics;

public interface IMaterialViewService {
	/** 安查询条件统计产品视图  **/
	List<MaterialStatistics> queryMaterial(MaterialStatistics condition);
	/** 产品视图记录数 **/
	int countMaterial(MaterialStatistics condition);
	/** 产品的指标 **/
	MaterialIndicators getIndicators(String year);
}
