package com.douniu.imshh.finance.storage.dao;

import java.util.List;

import com.douniu.imshh.finance.storage.domain.Deliver;

public interface IDeliverDao {
	List<Deliver> queryDeliverDetail(Deliver deliver);
	List<Deliver> queryDeliverNoPage(Deliver deliver);
	int countDeliverDetail(Deliver deliver);
	Deliver findDeliverById(String id);
	List<Deliver> findByOrder(String orderId);
	void insert(Deliver deliver);
	void batchInsert(List<Deliver> delivers);
	void update(Deliver deliver);
	void deleteById(String id);
}
