package com.douniu.imshh.finance.storage.service;

import java.util.List;

import com.douniu.imshh.finance.storage.domain.MaterialOut;

public interface IMaterialOutService {
	List<MaterialOut> query(MaterialOut out);
	List<MaterialOut> queryNoPage(MaterialOut out);
	int count(MaterialOut out);	
	MaterialOut getById(String id);
	void save(MaterialOut out);
	void delete(String id);
	void batchAdd(List<MaterialOut> outs);
}
