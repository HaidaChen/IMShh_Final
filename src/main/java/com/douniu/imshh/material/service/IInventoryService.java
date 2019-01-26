package com.douniu.imshh.material.service;

import java.util.List;

import com.douniu.imshh.common.ImportException;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.Inventory;
import com.douniu.imshh.material.domain.InventoryDetail;
import com.douniu.imshh.material.domain.MaterialFilter;

public interface IInventoryService {
	void inventory(Inventory inventory);
	PageResult getSystemInventory(MaterialFilter filter);
	void initCashInventory();
	void saveCacheItem(String materialId, float quantity);
	void importCacheItem(List<InventoryDetail> inventoryDetail);
	List<ImportException> checkImport(List<InventoryDetail> inventoryDetail);
	List<Inventory> queryInventory(MaterialFilter filter);
	PageResult queryInventoryDetailPageResult(MaterialFilter filter);	
	Inventory getById(String id);
}
