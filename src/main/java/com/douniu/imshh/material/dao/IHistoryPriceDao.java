package com.douniu.imshh.material.dao;

import java.util.List;

import com.douniu.imshh.material.domain.HistoryPrice;

public interface IHistoryPriceDao {
	List<HistoryPrice> getHistoryPrice(String supplierId);
	
	float getCurrentPrice(String supplierId);
	
	void insert(HistoryPrice price);
	
	void update(HistoryPrice price);
	
	void delete(String id);
}
