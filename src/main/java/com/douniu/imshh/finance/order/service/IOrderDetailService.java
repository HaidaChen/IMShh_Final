package com.douniu.imshh.finance.order.service;

import java.util.List;

import com.douniu.imshh.finance.order.domain.OrderDetail;

public interface IOrderDetailService {
	OrderDetail findById(String id);
	void batchAdd(List<OrderDetail> details);
	void deleteByOrderIdentify(String orderId);
	void killByOrderIdentify(String orderId);
}
