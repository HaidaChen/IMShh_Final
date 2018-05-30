package com.douniu.imshh.busdata.product.dao;

import java.util.List;

import com.douniu.imshh.busdata.product.domain.Product;

public interface IProductDao {
	List<Product> query(Product product);
	List<Product> queryNoPage(Product product);
	List<Product> queryByOrder(String orderIdt);
	int count(Product product);
	Product findById(String id);
	Product findByCode(String code);
	void insert(Product product);
	void batchInsert(List<Product> products);
	void update(Product product);
	void deleteById(String id);
}
