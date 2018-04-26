package com.douniu.imshh.finance.storage.dao;

import java.util.List;

import com.douniu.imshh.finance.storage.domain.Storage;

public interface IStorageDao {
	List<Storage> queryDetail(Storage storage);
	List<Storage> queryDetailNoPage(Storage storage);
	int countDetail(Storage storage);
	Storage findDetailById(String id);
	void insert(Storage storage);
	void batchInsert(List<Storage> storages);
	void update(Storage storage);
	void deleteById(String id);
	
	List<Storage> statisticsStorage(Storage storage);
	int countStorage(Storage storage);
}
