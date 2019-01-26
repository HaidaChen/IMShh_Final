package com.douniu.imshh.order.dao;

import java.util.List;

import com.douniu.imshh.order.domain.Order;
import com.douniu.imshh.order.domain.OrderAppointment;
import com.douniu.imshh.order.domain.OrderFilter;
import com.douniu.imshh.order.domain.OrderItem;
import com.douniu.imshh.order.domain.OrderProductDetail;

public interface IOrderDao {
	List<Order> getPageResult(OrderFilter filter);
	int count(OrderFilter filter);
	List<Order> query(OrderFilter filter);
	List<Order> getAll();
	Order getById(String id);
	
	List<OrderProductDetail> getOrderProductPageResult(OrderFilter filter);
	List<OrderProductDetail> queryOrderProduct(OrderFilter filter);
	int countOrderProduct(OrderFilter filter);
	
	void insert(Order order);
	void update(Order order);
	void delete(String id);
	
	void insertAppointment(OrderAppointment order);
	void updateAppointment(OrderAppointment order);
	void deleteAppointment(String orderId);
	
	void insertItems(List<OrderItem> items);
	void deleteItemsByOrderId(String id);
}
