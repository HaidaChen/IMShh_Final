package com.douniu.imshh.finance.order.dao;

import java.util.List;

import com.douniu.imshh.finance.order.domain.OrderDetail;

public interface IOrderDetailDao {
	List<OrderDetail> queryByOrder(String orderId);
	OrderDetail findById(String id);
	void killByOrderIdentify(String orderIdentify);
	void deleteByOrderIdentify(String orderIdentify);
	void batchInsert(List<OrderDetail> orderDetails);
}
