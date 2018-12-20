package com.douniu.imshh.material.dao;

import java.util.List;

import com.douniu.imshh.material.domain.BillDetail;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialOutBill;

public interface IMaterialOutDao {
	List<MaterialOutBill> getPageResult(MaterialFilter filter);
	int count(MaterialFilter filter);
	MaterialOutBill getById(String id);
	
	void insert(MaterialOutBill bill);
	void update(MaterialOutBill bill);
	void delete(String id);
	
	void insertDetails(List<BillDetail> details);
	void deleteDetailsByBillId(String id);
	/*List<MaterialOut> getPageResult(MaterialFilter filter);
	
	List<MaterialOut> query(MaterialFilter filter);
	
	int count(MaterialFilter filter);
	
	MaterialOut getById(String id);

	void insert(MaterialOut materialOut);
	
	void batchInsert(List<MaterialOut> materialOutList);
	
	void delete(String id);*/
}
