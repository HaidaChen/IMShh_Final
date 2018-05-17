package com.douniu.imshh.finance.account.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.douniu.imshh.finance.account.dao.IReceptionDao;
import com.douniu.imshh.finance.account.domain.Reception;
import com.douniu.imshh.finance.account.domain.Transaction;
import com.douniu.imshh.finance.account.service.IReceptionService;
import com.douniu.imshh.finance.order.domain.Order;
import com.douniu.imshh.finance.order.domain.OrderDetail;
import com.douniu.imshh.finance.order.service.IOrderService;
import com.douniu.imshh.finance.storage.domain.Deliver;

public class ReceptionService implements IReceptionService{
	private IReceptionDao dao;
	private IOrderService orderService;
	
	@Override
	public Reception statistics(String year) {
		return dao.statistics(year);
	}

	@Override
	public List<Reception> statisticsByCustomer(String customerName) {
		return dao.statisticsByCustomer("%"+customerName+"%");
	}

	@Override
	public void addReception(Deliver deliver) {
		Order order = orderService.getByNo(deliver.getOrderIdentify());
		String customerId = order.getCustId();
		String customerName = order.getCustName();
		DateFormat format = new SimpleDateFormat("yyyy-MM");  
        String month = format.format(deliver.getDeliverDate());  
		float amount = 0;
		List<OrderDetail> details = order.getDetails();
		for (OrderDetail detail : details){
			if (detail.getPdtNo().equals(deliver.getPdtNo()) && detail.getContent().equals(deliver.getContent())){
				amount = deliver.getAmount() * detail.getPriceRMB();
			}
		}
		Reception reception = new Reception();
		reception.setCustomerId(customerId);
		reception.setCustomerName(customerName);
		reception.setMonth(month);
		reception.setReception(amount);
		dao.addReception(reception);
	}

	@Override
	public void addPayment(Transaction transaction) {
		Reception reception = new Reception();
		reception.setCustomerName(transaction.getTranUser());
		
		DateFormat format = new SimpleDateFormat("yyyy-MM");  
        String month = format.format(transaction.getTranDate());  
		reception.setMonth(month);
		reception.setPayment(transaction.getTranAmount());
		dao.addPayment(reception);
	}

	public void setDao(IReceptionDao dao) {
		this.dao = dao;
	}

	public void setOrderService(IOrderService orderService) {
		this.orderService = orderService;
	}		
}
