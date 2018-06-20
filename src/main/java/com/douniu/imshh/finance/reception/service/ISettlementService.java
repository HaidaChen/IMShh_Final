package com.douniu.imshh.finance.reception.service;

import java.util.List;

import com.douniu.imshh.finance.reception.domain.Settlement;

public interface ISettlementService {
	List<Settlement> query(Settlement settlement);
	Settlement findById(String id);
	Settlement findLastOne();
	void insert(Settlement settlement);
	void update(Settlement settlement);
	void delete(Settlement settlement);
}
