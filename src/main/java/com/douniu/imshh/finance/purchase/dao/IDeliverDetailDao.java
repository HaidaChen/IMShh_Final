package com.douniu.imshh.finance.purchase.dao;

import java.util.List;

import com.douniu.imshh.finance.purchase.domain.DeliverDetail;

public interface IDeliverDetailDao {
	List<DeliverDetail> queryByPlan(String planId);
	List<DeliverDetail> query(DeliverDetail deliverDetail);
	int count(DeliverDetail deliverDetail);
	DeliverDetail findById(String id);
	void insert(DeliverDetail deliver);
	void update(DeliverDetail deliver);
	void delete(String id);
	void deleteByPlanId(String planId);
}
