package com.douniu.imshh.finance.order.service;

import java.util.List;

import com.douniu.imshh.finance.order.domain.OrderDetail;

public interface IOrderDetailService {
	List<OrderDetail> queryByOrder(String orderId);
	OrderDetail findById(String id);
	void batchAdd(List<OrderDetail> details);
	void deleteByOrderId(String orderId);
	void killByOrderId(String orderId);
}
