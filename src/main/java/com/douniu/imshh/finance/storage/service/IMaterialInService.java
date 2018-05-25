package com.douniu.imshh.finance.storage.service;

import java.util.List;

import com.douniu.imshh.finance.storage.domain.MaterialIn;

public interface IMaterialInService {
	List<MaterialIn> query(MaterialIn reception);
	List<MaterialIn> queryNoPage(MaterialIn reception);
	int count(MaterialIn reception);
	List<MaterialIn> queryBySupplier(MaterialIn reception);
	int countBySupplier(MaterialIn reception);
	MaterialIn getById(String id);
	void save(MaterialIn reception);
	void delete(String id);
	void batchAdd(List<MaterialIn> receptions);
}
