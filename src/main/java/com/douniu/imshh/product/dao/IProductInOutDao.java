package com.douniu.imshh.product.dao;

import java.util.Date;
import java.util.List;

import com.douniu.imshh.product.domain.ProductFilter;
import com.douniu.imshh.product.domain.ProductInOut;

public interface IProductInOutDao {
	void batchInsert(List<ProductInOut> details);
	void deleteByBill(String billId);
	
	List<ProductInOut> getTotalInOut(ProductFilter filter);
	int countTotalInOut(ProductFilter filter);
	List<ProductInOut> queryTotalInOut(ProductFilter filter);
	
	int getTotalInQuantity(String pdtId, Date startDate, Date endDate);
	int getTotalOutQuantity(String pdtId, Date startDate, Date endDate);
	List<ProductInOut> getInOutByProduct(String pdtId, Date startDate, Date endDate);
}
