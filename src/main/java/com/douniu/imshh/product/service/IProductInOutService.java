package com.douniu.imshh.product.service;

import java.util.List;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.product.domain.ProductBill;
import com.douniu.imshh.product.domain.ProductFilter;
import com.douniu.imshh.product.domain.ProductInOut;
import com.douniu.imshh.product.domain.ProductInOutMap;

public interface IProductInOutService {
	void insert(ProductBill bill);
	void update(ProductBill bill);
	void delete(String billId);
	PageResult getGlobalInOutPageResult(ProductFilter filter);
	List<ProductInOutMap> queryGlobalInOut(ProductFilter filter);
	List<ProductInOut> getInOutByProduct(String productId, String sPeriod, String ePeriod);
}
