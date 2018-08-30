package com.douniu.imshh.finance.dashboard.service.impl;

import java.util.List;

import com.douniu.imshh.finance.dashboard.dao.IProductViewDao;
import com.douniu.imshh.finance.dashboard.domain.ProductIndicators;
import com.douniu.imshh.finance.dashboard.domain.ProductStatistics;
import com.douniu.imshh.finance.dashboard.service.IProductViewService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class ProductViewService implements IProductViewService{
	private IProductViewDao dao;
	
	@Override
	public List<ProductStatistics> queryProduct(ProductStatistics productView) {
		ProductStatistics condition = LikeFlagUtil.appendLikeFlag(productView, new String[]{"pdtNo"});
		return dao.queryProduct(condition);
	}

	@Override
	public int countProduct(ProductStatistics productView) {
		ProductStatistics condition = LikeFlagUtil.appendLikeFlag(productView, new String[]{"pdtNo"});
		return dao.countProduct(condition);
	}

	@Override
	public ProductIndicators getIndicators(String year) {
		return dao.getIndicators(year);
	}

	public void setDao(IProductViewDao dao) {
		this.dao = dao;
	}

}
