package com.douniu.imshh.product.service;

import java.util.List;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.product.domain.Inventory;
import com.douniu.imshh.product.domain.ProductFilter;

public interface IInventoryService {
	void inventory(Inventory inventory);
	PageResult getSystemInventory(ProductFilter filter);
	void initCashInventory();
	void saveCacheItem(String productId, float quantity);
	List<Inventory> queryInventory(ProductFilter filter);
	PageResult queryInventoryDetailPageResult(ProductFilter filter);	
	Inventory getById(String id);
}
