package com.douniu.imshh.order.service;

import java.util.List;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.order.domain.Order;
import com.douniu.imshh.order.domain.OrderFilter;
import com.douniu.imshh.order.domain.OrderProductDetail;

public interface IOrderService {
	PageResult getPageResult(OrderFilter filter);
	List<Order> query(OrderFilter filter);
	List<Order> getAll();
	PageResult getOrderProductPageResult(OrderFilter filter);
	List<OrderProductDetail> queryOrderProduct(OrderFilter filter);
	Order getById(String id);
	void newOrder(Order order);
	void updateOrder(Order order);
	void deleteOrder(String id);
}
