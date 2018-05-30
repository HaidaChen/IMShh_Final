package com.douniu.imshh.finance.storage.dao;

import java.util.List;

import com.douniu.imshh.finance.storage.domain.ProductOut;

public interface IProductOutDao {
	List<ProductOut> queryDeliverDetail(ProductOut deliver);
	List<ProductOut> queryDeliverNoPage(ProductOut deliver);
	List<ProductOut> queryByCustomer(ProductOut deliver);
	int countDeliverDetail(ProductOut deliver);
	int countByCustomer(ProductOut deliver);
	ProductOut findDeliverById(String id);
	List<ProductOut> findByOrder(String orderIdentify);
	void insert(ProductOut deliver);
	void batchInsert(List<ProductOut> delivers);
	void update(ProductOut deliver);
	void deleteById(String id);
}
