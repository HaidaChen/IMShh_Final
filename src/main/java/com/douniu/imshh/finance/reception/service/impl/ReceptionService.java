package com.douniu.imshh.finance.reception.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.douniu.imshh.finance.account.domain.Transaction;
import com.douniu.imshh.finance.order.domain.Order;
import com.douniu.imshh.finance.order.domain.OrderDetail;
import com.douniu.imshh.finance.order.service.IOrderService;
import com.douniu.imshh.finance.reception.dao.IReceptionDao;
import com.douniu.imshh.finance.reception.domain.Reception;
import com.douniu.imshh.finance.reception.service.IReceptionService;
import com.douniu.imshh.finance.storage.domain.ProductOut;
import com.douniu.imshh.utils.LikeFlagUtil;

public class ReceptionService implements IReceptionService{
	private IReceptionDao dao;
	private IOrderService orderService;
	
	@Override
	public Reception statistics(Reception reception) {
		return dao.statistics(reception);
	}

	@Override
	public List<Reception> statisticsByOrder(Reception reception) {
		Reception condition = LikeFlagUtil.appendLikeFlag(reception, new String[]{"orderIdentify"});
		return dao.statisticsByOrder(condition);
	}

	@Override
	public void addReception(ProductOut deliver) {
		Order order = orderService.getByNo(deliver.getOrderIdentify());
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
		reception.setCustomerName(customerName);
		//reception.setMonth(month);
		reception.setReception(amount);
		dao.addReception(reception);
	}

	@Override
	public void addPayment(Transaction transaction) {
		Reception reception = new Reception();
		reception.setCustomerName(transaction.getTranUser());
		
		DateFormat format = new SimpleDateFormat("yyyy-MM");  
        String month = format.format(transaction.getTranDate());  
		//reception.setMonth(month);
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
