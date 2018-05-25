package com.douniu.imshh.finance.storage.dao;

import java.util.List;

import com.douniu.imshh.finance.storage.domain.MaterialIn;

public interface IMaterialInDao {
	List<MaterialIn> query(MaterialIn reception);
	List<MaterialIn> queryNoPage(MaterialIn reception);
	int count(MaterialIn reception);
	List<MaterialIn> queryBySupplier(MaterialIn reception);
	int countBySupplier(MaterialIn reception);
	MaterialIn findById(String id);
	void insert(MaterialIn reception);
	void batchInsert(List<MaterialIn> receptions);
	void update(MaterialIn reception);
	void deleteById(String id);
}
