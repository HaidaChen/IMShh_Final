package com.douniu.imshh.material.service;

import java.util.List;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.Inventory;
import com.douniu.imshh.material.domain.MaterialFilter;

public interface IInventoryService {
	void inventory(Inventory inventory);
	PageResult getSystemInventory(MaterialFilter filter);
	void initCashInventory();
	void saveCacheItem(String materialId, float quantity);
	List<Inventory> queryInventory(MaterialFilter filter);
	PageResult queryInventoryDetailPageResult(MaterialFilter filter);	
	Inventory getById(String id);
}
