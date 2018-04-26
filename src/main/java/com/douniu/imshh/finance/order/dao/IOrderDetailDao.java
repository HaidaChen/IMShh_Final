package com.douniu.imshh.finance.order.dao;

import java.util.List;

import com.douniu.imshh.finance.order.domain.OrderDetail;

public interface IOrderDetailDao {
	List<OrderDetail> queryByOrder(String orderId);
	void killByOrderId(String orderId);
	void deleteByOrderId(String orderId);
	void batchInsert(List<OrderDetail> orderDetails);
}
