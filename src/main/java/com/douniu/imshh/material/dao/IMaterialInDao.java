package com.douniu.imshh.material.dao;

import java.util.List;

import com.douniu.imshh.material.domain.BillDetail;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialInBill;

public interface IMaterialInDao {
	List<MaterialInBill> getPageResult(MaterialFilter filter);
	int count(MaterialFilter filter);
	MaterialInBill getById(String id);
	
	void insert(MaterialInBill materialIn);
	void update(MaterialInBill materialIn);
	void delete(String id);
	
	void insertDetails(List<BillDetail> details);
	void deleteDetailsByBillId(String id);
	
	/*List<MaterialIn> getPageResult(MaterialFilter filter);
	
	List<MaterialIn> query(MaterialFilter filter);
	
	int count(MaterialFilter filter);
	
	MaterialIn getById(String id);

	void insert(MaterialIn materialIn);
	
	void batchInsert(List<MaterialIn> materialInList);
	
	void delete(String id);*/
}
