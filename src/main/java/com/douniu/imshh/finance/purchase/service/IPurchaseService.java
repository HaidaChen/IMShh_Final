package com.douniu.imshh.finance.purchase.service;

import java.util.List;

import com.douniu.imshh.finance.purchase.domain.PurchasePlan;

public interface IPurchaseService {
	List<PurchasePlan> query(PurchasePlan purchase);
	//List<PurchasePlan> queryNoPage(PurchasePlan purchase);
	int count(PurchasePlan purchase);
	PurchasePlan getById(String id);
	void save(PurchasePlan purchase);
	void delete(String id);
	//void batchAdd(List<PurchasePlan> Purchases);
}
