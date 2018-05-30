package com.douniu.imshh.finance.storage.service;

import java.util.List;

import com.douniu.imshh.finance.storage.domain.ProductOut;

public interface IProductOutService {
	List<ProductOut> query(ProductOut deliver);
	List<ProductOut> queryNoPage(ProductOut deliver);
	List<ProductOut> queryByCustomer(ProductOut deliver);
	int count(ProductOut deliver);
	int countByCustomer(ProductOut deliver);
	ProductOut getById(String id);
	List<ProductOut> findByOrder(String orderIdentify);
	void save(ProductOut deliver);
	void delete(String id);
	void batchAdd(List<ProductOut> delivers);
}
