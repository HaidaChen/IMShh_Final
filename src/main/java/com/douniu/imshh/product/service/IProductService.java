package com.douniu.imshh.product.service;

import java.util.List;

import com.douniu.imshh.common.ImportException;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.product.domain.Product;
import com.douniu.imshh.product.domain.ProductFilter;

public interface IProductService {
	PageResult getPageResult(ProductFilter filter);
	List<Product> query(ProductFilter filter);
	List<Product> queryByOrder(String orderIdt);
	Product getById(String id);
	Product getByCode(String code);
	void newProduct(Product product);
	void updateProduct(Product product);
	//void save(Product product);
	void delete(String id);
	
	void importProduct(List<Product> productList);
	List<ImportException> checkImport(List<Product> productList);
	List<Product> exportProduct(ProductFilter filter);
	//void batchAdd(List<Product> products);
	void addStorage(String id, int storage);
}
