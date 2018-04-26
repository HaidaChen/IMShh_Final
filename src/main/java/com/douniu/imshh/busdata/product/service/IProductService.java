package com.douniu.imshh.busdata.product.service;

import java.util.List;

import com.douniu.imshh.busdata.product.domain.Product;

public interface IProductService {
	List<Product> query(Product product);
	List<Product> queryNoPage(Product product);
	int count(Product product);
	Product getById(String id);
	void save(Product product);
	void delete(String id);
	void batchAdd(List<Product> products);
}
