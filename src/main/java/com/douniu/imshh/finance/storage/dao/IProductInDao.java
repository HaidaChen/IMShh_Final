package com.douniu.imshh.finance.storage.dao;

import java.util.List;

import com.douniu.imshh.finance.storage.domain.ProductIn;

public interface IProductInDao {
	List<ProductIn> queryDetail(ProductIn storage);
	List<ProductIn> queryDetailNoPage(ProductIn storage);
	int countDetail(ProductIn storage);
	ProductIn findDetailById(String id);
	void insert(ProductIn storage);
	void batchInsert(List<ProductIn> storages);
	void update(ProductIn storage);
	void deleteById(String id);
	
	List<ProductIn> statisticsStorage(ProductIn storage);
	int countStorage(ProductIn storage);
}
