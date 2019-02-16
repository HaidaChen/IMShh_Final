package com.douniu.imshh.customer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.douniu.imshh.common.ImportException;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.customer.dao.ICustomerDao;
import com.douniu.imshh.customer.domain.Customer;
import com.douniu.imshh.customer.domain.CustomerFilter;
import com.douniu.imshh.customer.service.ICustomerService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class CustomerService implements ICustomerService{
	private ICustomerDao dao;
	
	@Override
	public PageResult getPageResult(CustomerFilter filter) {
		PageResult pr = new PageResult();
		CustomerFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"custName", "custPhone", "custContacts", "remark"});
		pr.setRows(dao.getPageResult(condition));
		pr.setTotal(dao.count(condition));
		return pr;
	}

	@Override
	public List<Customer> query(CustomerFilter filter) {
		CustomerFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"custName", "custPhone", "custContacts", "remark"});
		return dao.query(condition);
	}

	@Override
	public Customer getById(String id) {
		return dao.findById(id);
	}

	@Override
	public void newCustomer(Customer customer) {
		customer.setId(System.currentTimeMillis()+"");
		customer.setCreateDate(new Date());
		customer.setStatus(1);
		dao.insert(customer);
	}

	@Override
	public void updateCustomer(Customer customer) {
		dao.update(customer);
	}

	@Override
	public void delete(String id) {
		dao.deleteById(id);
	}

	@Override
	public List<ImportException> checkImport(List<Customer> customers) {
		List<ImportException> exceptions = new ArrayList<ImportException>();
		String exist_customer = "";
		List<Customer> allCustomer = query(new CustomerFilter());
		for (int i = 0; i < customers.size(); i++){
			if(allCustomer.contains(customers.get(i))){
				exist_customer += "," + (i+2);
			}
		}
		if (!exist_customer.equals("")){
			exceptions.add(new ImportException("存在相同的客户", "以及存在名称相同的客户", exist_customer.substring(1), ""));
		}
		return exceptions;
	}

	@Override
	public void importCustomer(List<Customer> customers) {
		dao.batchInsert(customers);
	}

	public void setDao(ICustomerDao dao) {
		this.dao = dao;
	}	
}
