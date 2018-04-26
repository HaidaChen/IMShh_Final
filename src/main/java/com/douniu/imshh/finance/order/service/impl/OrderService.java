package com.douniu.imshh.finance.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.douniu.imshh.finance.order.dao.IOrderDao;
import com.douniu.imshh.finance.order.domain.Order;
import com.douniu.imshh.finance.order.domain.OrderAndDetail;
import com.douniu.imshh.finance.order.domain.OrderDetail;
import com.douniu.imshh.finance.order.service.IOrderDetailService;
import com.douniu.imshh.finance.order.service.IOrderService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class OrderService implements IOrderService{

	private IOrderDao dao;
	private IOrderDetailService detailService;
	
	@Override
	public List<Order> query(Order order) {
		Order condition = LikeFlagUtil.appendLikeFlag(order, new String[]{"identify", "custName"});
		return dao.query(condition);
	}

	@Override
	public List<OrderAndDetail> queryNoPage(Order order) {
		Order condition = LikeFlagUtil.appendLikeFlag(order, new String[]{"identify", "custName"});
		List<Order> result = dao.queryNoPage(condition);
		return tileOrder(result);
	}

	@Override
	public int count(Order order) {
		Order condition = LikeFlagUtil.appendLikeFlag(order, new String[]{"identify", "custName"});
		return dao.count(condition);
	}

	@Override
	public Order getById(String id) {
		Order order = dao.findById(id);
		List<OrderDetail> details = detailService.queryByOrder(id);
		order.setDetails(details);
		return order;
	}

	@Override
	public void save(Order order) {
		if (order.getId().equals("")){
			String orderId = System.currentTimeMillis()+"";
			order.setId(orderId);
			order.setStatus(1);
			for(OrderDetail detail : order.getDetails()){
				detail.setOrderId(orderId);
			}
			detailService.batchAdd(order.getDetails());
			dao.insert(order);
		}else{
			detailService.killByOrderId(order.getId());
			for(OrderDetail detail : order.getDetails()){
				detail.setOrderId(order.getId());
			}
			detailService.batchAdd(order.getDetails());
			dao.update(order);
		}
	}

	@Override
	public void delete(String id) {
		detailService.deleteByOrderId(id);
		dao.deleteById(id);
	}

	@Override
	public void batchAdd(List<OrderAndDetail> orderandDetails) {
		List<String> identifys = new ArrayList<String>();
		List<Order> orders = new ArrayList<Order>();
		List<OrderDetail> details = new ArrayList<OrderDetail>();
		int index = 0;
		for (OrderAndDetail orderAndDetail : orderandDetails){
			String identify = orderAndDetail.getIdentify();
			if (!identifys.contains(identify)){
				String orderId = System.currentTimeMillis() + "" + index++;
				Order order = new Order(orderId, orderAndDetail.getIdentify(), orderAndDetail.getCustName(), orderAndDetail.getOrderDate(), orderAndDetail.getAmount(), orderAndDetail.getRemark());
				orders.add(order);
				identifys.add(identify);
			}
			String detailId = System.currentTimeMillis() + "" + index++;
			String orderId = "";
			for (Order order : orders){
				if (order.getIdentify().equals(orderAndDetail.getIdentify())){
					orderId = order.getId();
					break;
				}
			}
			OrderDetail detail = new OrderDetail(detailId, orderId, orderAndDetail.getPdtNo(), orderAndDetail.getPdtName(), orderAndDetail.getContent(),orderAndDetail.getQuantity(), orderAndDetail.getPriceRMB(), orderAndDetail.getPriceDollar(), orderAndDetail.getTotlemnt(), orderAndDetail.getDetailRemark() );
			details.add(detail);
		}
		
		dao.batchInsert(orders);
		detailService.batchAdd(details);
	}
	
	private List<OrderAndDetail> tileOrder(List<Order> orderList){
		List<OrderAndDetail> orderAndDetails = new ArrayList<OrderAndDetail>();
		for (Order order : orderList){
			for (OrderDetail detail : order.getDetails()){
				OrderAndDetail orderAndDetail = new OrderAndDetail(order.getIdentify(), order.getCustName(), 
						order.getOrderDate(), order.getAmount(), order.getRemark(), detail.getPdtNo(),
						detail.getPdtName(), detail.getContent(), detail.getQuantity(), detail.getPriceRMB(), 
						detail.getPriceDollar(), detail.getTotlemnt(), detail.getProgress(), detail.getRemark());
				orderAndDetails.add(orderAndDetail);
			} 
		}
		return orderAndDetails;
	}

	public void setDao(IOrderDao dao) {
		this.dao = dao;
	}

	public void setDetailService(IOrderDetailService detailService) {
		this.detailService = detailService;
	}
	
	

}
