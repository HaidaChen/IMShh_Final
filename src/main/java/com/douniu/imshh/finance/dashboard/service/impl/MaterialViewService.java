package com.douniu.imshh.finance.dashboard.service.impl;

import java.util.List;

import com.douniu.imshh.finance.dashboard.dao.IMaterialViewDao;
import com.douniu.imshh.finance.dashboard.domain.MaterialIndicators;
import com.douniu.imshh.finance.dashboard.domain.MaterialStatistics;
import com.douniu.imshh.finance.dashboard.service.IMaterialViewService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class MaterialViewService implements IMaterialViewService{
	private IMaterialViewDao dao;
	
	@Override
	public List<MaterialStatistics> queryMaterial(MaterialStatistics materialView) {
		MaterialStatistics condition = LikeFlagUtil.appendLikeFlag(materialView, new String[]{"materialName"});
		return dao.queryMaterial(condition);
	}

	@Override
	public int countMaterial(MaterialStatistics materialView) {
		MaterialStatistics condition = LikeFlagUtil.appendLikeFlag(materialView, new String[]{"materialName"});
		return dao.countMaterial(condition);
	}

	@Override
	public MaterialIndicators getIndicators(String year) {
		return dao.getIndicators(year);
	}

	public void setDao(IMaterialViewDao dao) {
		this.dao = dao;
	}

}
