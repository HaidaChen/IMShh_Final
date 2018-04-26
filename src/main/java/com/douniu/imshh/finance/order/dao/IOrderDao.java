package com.douniu.imshh.finance.order.dao;

import java.util.List;

import com.douniu.imshh.finance.order.domain.Order;

public interface IOrderDao {
	List<Order> query(Order order);
	List<Order> queryNoPage(Order order);
	int count(Order order);
	Order findById(String id);
	void insert(Order Order);
	void batchInsert(List<Order> orders);
	void update(Order order);
	void deleteById(String id);
}
