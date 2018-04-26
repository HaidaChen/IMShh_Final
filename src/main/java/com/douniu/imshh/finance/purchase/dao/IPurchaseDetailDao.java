package com.douniu.imshh.finance.purchase.dao;

import java.util.List;

import com.douniu.imshh.finance.purchase.domain.PurchaseDetail;

public interface IPurchaseDetailDao {
	List<PurchaseDetail> queryByPlan(String planId);
	PurchaseDetail findById(String id);
	void killByPlanId(String planId);
	void deleteByPlanId(String planId);
	void batchInsert(List<PurchaseDetail> purchaseDetails);
}
