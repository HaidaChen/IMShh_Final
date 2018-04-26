package com.douniu.imshh.finance.purchase.service;

import java.util.List;

import com.douniu.imshh.finance.purchase.domain.PurchaseDetail;

public interface IPurchaseDetailService {
	List<PurchaseDetail> queryByPlan(String planId);
	PurchaseDetail queryById(String id);
	void batchAdd(List<PurchaseDetail> details);
	void deleteByPlanId(String planId);
	void killByPlanId(String planId);
}
