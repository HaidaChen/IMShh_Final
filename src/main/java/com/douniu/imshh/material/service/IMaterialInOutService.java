package com.douniu.imshh.material.service;

import java.util.List;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.MaterialBill;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialInOut;
import com.douniu.imshh.material.domain.MaterialInOutMap;

public interface IMaterialInOutService {
	void insert(MaterialBill bill);
	void update(MaterialBill bill);
	void delete(String billId);
	PageResult getGlobalInOutPageResult(MaterialFilter filter);
	List<MaterialInOutMap> queryGlobalInOut(MaterialFilter filter);
	List<MaterialInOut> getInOutByMaterial(String materialId, String sPeriod, String ePeriod);
}
