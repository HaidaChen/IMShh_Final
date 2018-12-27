package com.douniu.imshh.product.dao;

import java.util.List;

import com.douniu.imshh.product.domain.BillDetail;
import com.douniu.imshh.product.domain.ProductFilter;
import com.douniu.imshh.product.domain.ProductOutBill;

public interface IProductOutDao {
	List<ProductOutBill> getPageResult(ProductFilter filter);
	int count(ProductFilter filter);
	List<ProductOutBill> query(ProductFilter filter);
	ProductOutBill getById(String id);
	
	void insert(ProductOutBill productOut);
	void update(ProductOutBill productOut);
	void delete(String id);
	
	void insertDetails(List<BillDetail> details);
	void deleteDetailsByBillId(String id);
}
