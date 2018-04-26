package com.douniu.imshh.finance.storage.service;

import java.util.List;

import com.douniu.imshh.finance.storage.domain.Storage;

public interface IStorageService {
	List<Storage> queryDetail(Storage storage);
	List<Storage> queryDetailNoPage(Storage storage);
	int count(Storage storage);
	Storage getById(String id);
	void save(Storage storage);
	void delete(String id);
	void batchAdd(List<Storage> storages);
	
	List<Storage> statisticsStorage(Storage storage);
	int countStorage(Storage storage);
}
