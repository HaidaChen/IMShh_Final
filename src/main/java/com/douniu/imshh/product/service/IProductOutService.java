package com.douniu.imshh.product.service;

import java.util.List;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.product.domain.ProductFilter;
import com.douniu.imshh.product.domain.ProductOutBill;
import com.douniu.imshh.product.domain.ProductOutTableRow;

public interface IProductOutService {
	PageResult getPageResult(ProductFilter filter);
	List<ProductOutBill> query(ProductFilter filter);
	ProductOutBill getById(String id);
	void newProductOut(ProductOutBill productOut);
	void updateProductOut(ProductOutBill productOut);
	void deleteProductOut(String id);
	
	List<ProductOutTableRow> exportBill(ProductFilter filter);
}
