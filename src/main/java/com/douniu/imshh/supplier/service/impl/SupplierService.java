package com.douniu.imshh.supplier.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.douniu.imshh.common.ImportException;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.supplier.dao.ISupplierDao;
import com.douniu.imshh.supplier.domain.Supplier;
import com.douniu.imshh.supplier.domain.SupplierFilter;
import com.douniu.imshh.supplier.service.ISupplierService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class SupplierService implements ISupplierService{

	private ISupplierDao dao;
	
	@Override
	public PageResult getPageResult(SupplierFilter filter) {
		PageResult pr = new PageResult();
		SupplierFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"suppName", "suppPhone", "suppContacts", "remark"});
		pr.setRows(dao.getPageResult(condition));
		pr.setTotal(dao.count(condition));
		return pr;
	}

	@Override
	public List<Supplier> query(SupplierFilter filter) {
		// TODO Auto-generated method stub
		SupplierFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"suppName", "suppPhone", "suppContacts", "remark"});
		return dao.query(condition);
	}

	@Override
	public Supplier getById(String id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}
	
	@Override
	public void newSupplier(Supplier supplier) {
		supplier.setId(System.currentTimeMillis()+"");
		supplier.setCreateDate(new Date());
		supplier.setStatus(1);
		dao.insert(supplier);
	}

	@Override
	public void updateSupplier(Supplier supplier) {
		dao.update(supplier);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		dao.deleteById(id);
	}

	@Override
	public List<ImportException> checkImport(List<Supplier> suppliers) {
		List<ImportException> exceptions = new ArrayList<ImportException>();
		String exist_supplier = "";
		List<Supplier> allSupplier = query(new SupplierFilter());
		for (int i = 0; i < suppliers.size(); i++){
			if(allSupplier.contains(suppliers.get(i))){
				exist_supplier += "," + (i+2);
			}
		}
		if (!exist_supplier.equals("")){
			exceptions.add(new ImportException("存在相同的供应商", "以及存在名称相同的供应商", exist_supplier.substring(1), ""));
		}
		return exceptions;
	}

	@Override
	public void importSupplier(List<Supplier> suppliers) {
		dao.batchInsert(suppliers);
	}

	public void setDao(ISupplierDao dao) {
		this.dao = dao;
	}
}
