package com.douniu.imshh.material.dao;

import java.util.List;

import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialIn;
import com.douniu.imshh.material.domain.MaterialInDetail;

public interface IMaterialInDao {
	List<MaterialIn> getPageResult(MaterialFilter filter);
	int count(MaterialFilter filter);
	MaterialIn getById(String id);
	
	void insert(MaterialIn materialIn);
	void update(MaterialIn materialIn);
	void delete(String id);
	
	void insertDetails(List<MaterialInDetail> details);
	void deleteDetailsByBillId(String id);
	
	/*List<MaterialIn> getPageResult(MaterialFilter filter);
	
	List<MaterialIn> query(MaterialFilter filter);
	
	int count(MaterialFilter filter);
	
	MaterialIn getById(String id);

	void insert(MaterialIn materialIn);
	
	void batchInsert(List<MaterialIn> materialInList);
	
	void delete(String id);*/
}
