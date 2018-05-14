package com.douniu.imshh.busdata.customer.dao;

import java.util.List;

import com.douniu.imshh.busdata.customer.domain.Customer;

public interface ICustomerDao {
	List<Customer> query(Customer customer);
	List<Customer> queryNoPage(Customer customer);
	int count(Customer customer);
	Customer findById(String id);
	Customer findByName(String custname);
	void insert(Customer customer);
	void batchInsert(List<Customer> customers);
	void update(Customer customer);
	void deleteById(String id);
}
