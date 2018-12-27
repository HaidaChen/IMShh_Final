package com.douniu.imshh.product.dao;

import java.util.List;

import com.douniu.imshh.product.domain.Inventory;
import com.douniu.imshh.product.domain.InventoryDetail;
import com.douniu.imshh.product.domain.ProductFilter;

public interface IInventoryDao {
	void saveInventory(Inventory inventory);
	void saveInventoryDetail(String inventoryId);
	void updateStorage();
	void markInventory();
	void removeCash();
	//void initCache();
	
	int existCacheItem(String productId);
	void insertCacheItem(String productId, float quantity);
	void updateCacheItem(String productId, float quantity);
	
	List<InventoryDetail> queryStorageWithCachePageResult(ProductFilter filter);
	int countStorageWithCache(ProductFilter filter);
	
	List<Inventory> queryInventory(ProductFilter filter);
	List<InventoryDetail> queryInventoryDetailByPage(ProductFilter filter);
	int countInventoryDetail(ProductFilter filter);
	Inventory getById(String id);
}
