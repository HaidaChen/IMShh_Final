package com.douniu.imshh.finance.purchase.dao;

import java.util.List;

import com.douniu.imshh.finance.purchase.domain.PurchasePlan;

public interface IPurchaseDao {
	List<PurchasePlan> query(PurchasePlan purchase);
	//List<PurchasePlan> queryNoPage(PurchasePlan purchase);
	int count(PurchasePlan purchase);
	PurchasePlan findById(String id);
	void insert(PurchasePlan purchase);
	//void batchInsert(List<PurchasePlan> purchase);
	void update(PurchasePlan purchase);
	void deleteById(String id);
}
