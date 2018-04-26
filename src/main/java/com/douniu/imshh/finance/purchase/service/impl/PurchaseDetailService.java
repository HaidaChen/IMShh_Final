package com.douniu.imshh.finance.purchase.service.impl;

import java.util.List;

import com.douniu.imshh.finance.purchase.dao.IPurchaseDetailDao;
import com.douniu.imshh.finance.purchase.domain.PurchaseDetail;
import com.douniu.imshh.finance.purchase.service.IPurchaseDetailService;

public class PurchaseDetailService implements IPurchaseDetailService{
	private IPurchaseDetailDao dao;
	
	public void setDao(IPurchaseDetailDao dao) {
		this.dao = dao;
	}

	@Override
	public List<PurchaseDetail> queryByPlan(String planId) {
		// TODO Auto-generated method stub
		return dao.queryByPlan(planId);
	}

	@Override
	public void batchAdd(List<PurchaseDetail> details) {
		dao.batchInsert(details);
	}

	@Override
	public void deleteByPlanId(String planId) {
		dao.deleteByPlanId(planId);
	}

	@Override
	public void killByPlanId(String planId) {
		dao.killByPlanId(planId);
	}

	@Override
	public PurchaseDetail queryById(String id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}
}
