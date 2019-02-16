package com.douniu.imshh.customer.dao;

import java.util.List;

import com.douniu.imshh.customer.domain.Customer;
import com.douniu.imshh.customer.domain.CustomerFilter;

public interface ICustomerDao {
	List<Customer> getPageResult(CustomerFilter filter);
	List<Customer> query(CustomerFilter filter);
	int count(CustomerFilter filter);
	boolean exist(CustomerFilter filter);
	Customer findById(String id);
	void insert(Customer customer);
	void batchInsert(List<Customer> customers);
	void update(Customer customer);
	void deleteById(String id);
}
