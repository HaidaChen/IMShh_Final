package com.douniu.imshh.order.service.impl;

import java.util.List;

import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.order.dao.IOrderDao;
import com.douniu.imshh.order.domain.Order;
import com.douniu.imshh.order.domain.OrderAppointment;
import com.douniu.imshh.order.domain.OrderFilter;
import com.douniu.imshh.order.domain.OrderItem;
import com.douniu.imshh.order.domain.OrderProductDetail;
import com.douniu.imshh.order.service.IOrderService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class OrderService implements IOrderService{
	private IOrderDao dao;
	
	@Override
	public PageResult getPageResult(OrderFilter filter) {
		PageResult pr = new PageResult();
		OrderFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"identify"});
		pr.setRows(dao.getPageResult(condition));
		pr.setTotal(dao.count(condition));
		return pr;
	}
	
	@Override
	public List<Order> query(OrderFilter filter) {
		OrderFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"identify"});
		return dao.query(condition);
	}
	
	@Override
	public List<Order> getAll(){
		return dao.getAll();
	}
	
	/**
	 * 列举所有成品的订购、生产、交付、库存情况，不按月的汇总
	 * */
	public PageResult getOrderProductPageResult(OrderFilter filter) {
		PageResult pr = new PageResult();
		pr.setRows(dao.getOrderProductPageResult(filter));
		pr.setTotal(dao.countOrderProduct(filter));
		return pr;
	}
	
	@Override
	public List<OrderProductDetail> queryOrderProduct(OrderFilter filter) {
		return dao.queryOrderProduct(filter);
	}

	@Override
	public Order getById(String id) {
		return dao.getById(id);
	}

	@Override
	public void newOrder(Order order) {
		if (order.getOrderType().equals("1")
				&& order.getAppointment().getExchangeRate() != 0){
			order.setAmountRMB(order.getTotalAmount() * order.getAppointment().getExchangeRate());
		}
		IDInjector.injector(order);
		List<OrderItem> items = order.getItems();
		for (OrderItem item : items){
			item.setOrderId(order.getId());
		}
		IDInjector.injector(items);
		dao.insertItems(items);
		OrderAppointment appointment = order.getAppointment();
		appointment.setOrderId(order.getId());
		dao.insertAppointment(appointment);
		dao.insert(order);
		
	}

	@Override
	public void updateOrder(Order order) {
		if (order.getOrderType().equals("1")
				&& order.getAppointment().getExchangeRate() != 0){
			order.setAmountRMB(order.getTotalAmount() * order.getAppointment().getExchangeRate());
		}
		dao.update(order);
		dao.updateAppointment(order.getAppointment());
		dao.deleteItemsByOrderId(order.getId());
		List<OrderItem> items = order.getItems();
		for (OrderItem item : items){
			item.setOrderId(order.getId());
		}
		IDInjector.injector(items);
		dao.insertItems(items);
	}

	@Override
	public void deleteOrder(String id) {
		dao.deleteItemsByOrderId(id);
		//dao.deleteAppointment(id);
		dao.delete(id);
	}

	public void setDao(IOrderDao dao) {
		this.dao = dao;
	}	
}
