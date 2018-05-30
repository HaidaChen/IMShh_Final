package com.douniu.imshh.finance.storage.service;

import java.util.List;

import com.douniu.imshh.finance.storage.domain.ProductIn;

public interface IProductInService {
	List<ProductIn> queryDetail(ProductIn storage);
	List<ProductIn> queryDetailNoPage(ProductIn storage);
	int count(ProductIn storage);
	ProductIn getById(String id);
	void save(ProductIn storage);
	void delete(String id);
	void batchAdd(List<ProductIn> storages);
	
	List<ProductIn> statisticsStorage(ProductIn storage);
	int countStorage(ProductIn storage);
}
