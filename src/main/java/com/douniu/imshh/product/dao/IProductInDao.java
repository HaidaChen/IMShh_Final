package com.douniu.imshh.product.dao;

import java.util.List;

import com.douniu.imshh.product.domain.BillDetail;
import com.douniu.imshh.product.domain.ProductFilter;
import com.douniu.imshh.product.domain.ProductInBill;

public interface IProductInDao {
	List<ProductInBill> getPageResult(ProductFilter filter);
	int count(ProductFilter filter);
	List<ProductInBill> query(ProductFilter filter);
	ProductInBill getById(String id);
	
	void insert(ProductInBill productIn);
	void update(ProductInBill productIn);
	void delete(String id);
	
	void insertDetails(List<BillDetail> details);
	void deleteDetailsByBillId(String id);
}
