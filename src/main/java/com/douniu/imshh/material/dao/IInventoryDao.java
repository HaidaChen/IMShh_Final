package com.douniu.imshh.material.dao;

import java.util.List;

import com.douniu.imshh.material.domain.Inventory;
import com.douniu.imshh.material.domain.InventoryDetail;
import com.douniu.imshh.material.domain.MaterialFilter;

public interface IInventoryDao {
	void saveInventory(Inventory inventory);
	void saveInventoryDetail(String inventoryId);
	void updateStorage();
	void markInventory();
	void removeCash();
	//void initCache();
	
	int existCacheItem(String materialId);
	void insertCacheItem(String materialId, float quantity);
	void updateCacheItem(String materialId, float quantity);
	void batchInsertCacheItem(List<InventoryDetail> details);
	
	List<InventoryDetail> queryStorageWithCachePageResult(MaterialFilter filter);
	int countStorageWithCache(MaterialFilter filter);
	
	List<Inventory> queryInventory(MaterialFilter filter);
	List<InventoryDetail> queryInventoryDetailByPage(MaterialFilter filter);
	int countInventoryDetail(MaterialFilter filter);
	Inventory getById(String id);
}
