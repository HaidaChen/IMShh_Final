package com.douniu.imshh.material.service.impl;

import java.util.List;

import com.douniu.imshh.material.dao.IHistoryPriceDao;
import com.douniu.imshh.material.domain.HistoryPrice;
import com.douniu.imshh.material.service.IHistoryPriceService;

public class HistoryPriceService implements IHistoryPriceService{
	private IHistoryPriceDao dao;

	@Override
	public List<HistoryPrice> getHistoryPrice(String supplierId) {
		return dao.getHistoryPrice(supplierId);
	}

	@Override
	public float getCurrentPrice(String supplierId) {
		return dao.getCurrentPrice(supplierId);
	}

	@Override
	public void newPrice(HistoryPrice price) {
		dao.insert(price);
	}

	@Override
	public void updatePrice(HistoryPrice price) {
		dao.update(price);
	}

	@Override
	public void deletePrice(String id) {
		dao.delete(id);
	}

	public void setDao(IHistoryPriceDao dao) {
		this.dao = dao;
	}	
}
