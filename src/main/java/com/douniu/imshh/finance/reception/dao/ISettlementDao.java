package com.douniu.imshh.finance.reception.dao;

import java.util.List;

import com.douniu.imshh.finance.reception.domain.SettDetail;
import com.douniu.imshh.finance.reception.domain.Settlement;

public interface ISettlementDao {
	List<Settlement> query(Settlement settlement);
	Settlement findById(String id);
	Settlement findLastOne();
	void insert(Settlement settlement);
	void insertDetails(List<SettDetail> details);
	void update(Settlement settlement);
	void delete(Settlement settlement);
}
