package com.douniu.imshh.finance.storage.service;

import java.util.List;

import com.douniu.imshh.finance.storage.domain.MaterialStorage;

public interface IMaterialStorageService {
	List<MaterialStorage> statisticsStorage(MaterialStorage materialStorage);
	int countStorage(MaterialStorage materialStorage);
}
