package com.douniu.imshh.finance.order.service;

import java.util.List;

import com.douniu.imshh.finance.order.domain.Order;
import com.douniu.imshh.finance.order.domain.OrderAndDetail;

public interface IOrderService {
	List<Order> query(Order order);
	List<OrderAndDetail> queryNoPage(Order order);
	int count(Order order);
	Order getById(String id);
	void save(Order order);
	void delete(String id);
	void batchAdd(List<OrderAndDetail> orders);
}
