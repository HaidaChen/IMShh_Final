package com.douniu.imshh.finance.storage.service;

import java.util.List;

import com.douniu.imshh.finance.storage.domain.Deliver;

public interface IDeliverService {
	List<Deliver> query(Deliver deliver);
	List<Deliver> queryNoPage(Deliver deliver);
	int count(Deliver deliver);
	Deliver getById(String id);
	List<Deliver> findByOrder(String orderIdentify);
	void save(Deliver deliver);
	void delete(String id);
	void batchAdd(List<Deliver> delivers);
}
