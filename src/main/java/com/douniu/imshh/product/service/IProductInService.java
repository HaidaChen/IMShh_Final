package com.douniu.imshh.product.service;

import java.util.List;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.product.domain.ProductFilter;
import com.douniu.imshh.product.domain.ProductInBill;
import com.douniu.imshh.product.domain.ProductInTableRow;

public interface IProductInService {
	PageResult getPageResult(ProductFilter filter);
	List<ProductInBill> query(ProductFilter filter);
	ProductInBill getById(String id);
	void newProductIn(ProductInBill productIn);
	void updateProductIn(ProductInBill productIn);
	void deleteProductIn(String id);
	
	List<ProductInTableRow> exportBill(ProductFilter filter);
}
