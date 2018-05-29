package com.douniu.imshh.finance.storage.dao;

import java.util.List;

import com.douniu.imshh.finance.storage.domain.MaterialOut;

public interface IMaterialOutDao {
	List<MaterialOut> query(MaterialOut out);
	List<MaterialOut> queryNoPage(MaterialOut out);
	int count(MaterialOut out);
	MaterialOut findById(String id);
	void insert(MaterialOut out);
	void batchInsert(List<MaterialOut> outs);
	void update(MaterialOut out);
	void deleteById(String id);
}
