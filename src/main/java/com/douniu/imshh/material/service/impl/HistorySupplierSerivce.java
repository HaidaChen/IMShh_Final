package com.douniu.imshh.material.service.impl;

import java.util.List;

import com.douniu.imshh.material.dao.IHistorySupplierDao;
import com.douniu.imshh.material.domain.HistorySupplier;
import com.douniu.imshh.material.service.IHistoryPriceService;
import com.douniu.imshh.material.service.IHistorySupplierService;

public class HistorySupplierSerivce implements IHistorySupplierService{
	private IHistorySupplierDao dao;
	private IHistoryPriceService priceService;
	
	@Override
	public List<HistorySupplier> getHistorySupplier(String materialId) {
		return dao.getHistorySupplier(materialId);
	}

	@Override
	public HistorySupplier getById(String id) {
		HistorySupplier supplier = dao.findById(id);
		supplier.setHistoryPrices(priceService.getHistoryPrice(id));
		return supplier;
	}

	@Override
	public void newSupplier(HistorySupplier supplier) {
		dao.insert(supplier);
	}

	@Override
	public void deleteSupplier(String id) {
		dao.delete(id);
	}

	public void setDao(IHistorySupplierDao dao) {
		this.dao = dao;
	}

	public void setPriceService(IHistoryPriceService priceService) {
		this.priceService = priceService;
	}
	
}
