package com.douniu.imshh.order.service;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.order.domain.Order;
import com.douniu.imshh.order.domain.OrderFilter;

public interface IOrderService {
	PageResult getPageResult(OrderFilter filter);
	Order getById(String id);
	void newOrder(Order order);
	void updateOrder(Order order);
	void deleteOrder(String id);
}
