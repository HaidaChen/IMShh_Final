package com.douniu.imshh.busdata.supplier.service.impl;

import java.util.Date;
import java.util.List;

import com.douniu.imshh.busdata.supplier.dao.ISupplierDao;
import com.douniu.imshh.busdata.supplier.domain.Supplier;
import com.douniu.imshh.busdata.supplier.service.ISupplierService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class SupplierService implements ISupplierService{

	private ISupplierDao dao;
	
	@Override
	public List<Supplier> query(Supplier supplier) {
		// TODO Auto-generated method stub
		Supplier condition = LikeFlagUtil.appendLikeFlag(supplier, new String[]{"condition"});
		return dao.query(condition);
	}

	@Override
	public List<Supplier> queryNoPage(Supplier supplier) {
		// TODO Auto-generated method stub
		Supplier condition = LikeFlagUtil.appendLikeFlag(supplier, new String[]{"condition"});
		return dao.queryNoPage(condition);
	}

	@Override
	public int count(Supplier supplier) {
		Supplier condition = LikeFlagUtil.appendLikeFlag(supplier, new String[]{"condition"});
		return dao.count(condition);
	}

	@Override
	public Supplier getById(String id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	public void save(Supplier supplier) {
		// TODO Auto-generated method stub
		if (supplier.getId().equals("")){
			supplier.setId(System.currentTimeMillis()+"");
			supplier.setCreateDate(new Date());
			supplier.setStatus(1);
			dao.insert(supplier);
		}else{
			dao.update(supplier);
		}
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		dao.deleteById(id);
	}

	@Override
	public void batchAdd(List<Supplier> suppliers) {
		// TODO Auto-generated method stub
		dao.batchInsert(suppliers);
	}

	public void setDao(ISupplierDao dao) {
		this.dao = dao;
	}
}
