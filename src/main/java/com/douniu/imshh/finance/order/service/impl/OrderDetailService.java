package com.douniu.imshh.finance.order.service.impl;

import java.util.List;

import com.douniu.imshh.finance.order.dao.IOrderDetailDao;
import com.douniu.imshh.finance.order.domain.OrderDetail;
import com.douniu.imshh.finance.order.service.IOrderDetailService;

public class OrderDetailService implements IOrderDetailService{
	private IOrderDetailDao dao;
	
	@Override
	public OrderDetail findById(String id){
		return dao.findById(id);
	}

	@Override
	public void batchAdd(List<OrderDetail> details) {
		dao.batchInsert(details);
	}	
	
	@Override
	public void killByOrderId(String orderId) {
		dao.killByOrderId(orderId);
	}

	@Override
	public void deleteByOrderId(String orderId) {
		dao.deleteByOrderId(orderId);
	}

	public void setDao(IOrderDetailDao dao) {
		this.dao = dao;
	}
	
}
