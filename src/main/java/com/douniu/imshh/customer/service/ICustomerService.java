package com.douniu.imshh.customer.service;

import java.util.List;

import com.douniu.imshh.common.ImportException;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.customer.domain.Customer;
import com.douniu.imshh.customer.domain.CustomerFilter;

public interface ICustomerService {
	PageResult getPageResult(CustomerFilter filter);
	List<Customer> query(CustomerFilter filter);
	Customer getById(String id);
	void newCustomer(Customer customer);
	void updateCustomer(Customer customer);
	void delete(String id);
	List<ImportException> checkImport(List<Customer> customers);
	void importCustomer(List<Customer> customers);
}
