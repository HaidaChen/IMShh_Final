package com.douniu.imshh.material.dao;

import java.util.Date;
import java.util.List;

import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialInOut;

public interface IMaterialInOutDao {
	void batchInsert(List<MaterialInOut> details);
	void deleteByBill(String billId);
	
	List<MaterialInOut> getTotalInOut(MaterialFilter filter);
	int countTotalInOut(MaterialFilter filter);
	List<MaterialInOut> queryTotalInOut(MaterialFilter filter);
	
	float getTotalInQuantity(String mtlId, Date startDate, Date endDate);
	float getTotalOutQuantity(String mtlId, Date startDate, Date endDate);
	List<MaterialInOut> getInOutByMaterial(String mtlId, Date startDate, Date endDate);
}
