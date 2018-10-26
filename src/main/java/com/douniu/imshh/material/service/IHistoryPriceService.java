package com.douniu.imshh.material.service;

import java.util.List;

import com.douniu.imshh.material.domain.HistoryPrice;

public interface IHistoryPriceService {
	/**
	 * 获得单个原材料的历史价格
	 * @param material
	 * @return
	 */
	List<HistoryPrice> getHistoryPrice(String supplierId);
	
	float getCurrentPrice(String supplierId);
	
	void newPrice(HistoryPrice price);
	
	void updatePrice(HistoryPrice price);
	
	void deletePrice(String id);
}
