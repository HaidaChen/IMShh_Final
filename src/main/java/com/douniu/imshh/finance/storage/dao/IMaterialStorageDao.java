package com.douniu.imshh.finance.storage.dao;

import java.util.List;

import com.douniu.imshh.finance.storage.domain.MaterialStorage;

public interface IMaterialStorageDao {
	List<MaterialStorage> statisticsStorage(MaterialStorage materialStorage);
	int countStorage(MaterialStorage materialStorage);
}
