package com.douniu.imshh.product.dao;

import java.util.List;

import com.douniu.imshh.product.domain.Product;
import com.douniu.imshh.product.domain.ProductFilter;

public interface IProductDao {
	List<Product> getPageResult(ProductFilter product);
	List<Product> query(ProductFilter product);
	List<Product> queryByOrder(String orderIdt);
	int count(ProductFilter product);
	Product findById(String id);
	Product findByCode(String code);
	void insert(Product product);
	void batchInsert(List<Product> products);
	void update(Product product);
	void setStorage(String id, int storage);
	void deleteById(String id);
}
