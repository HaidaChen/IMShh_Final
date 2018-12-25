package com.douniu.imshh.order.domain;

import java.util.Date;
import java.util.List;

import com.douniu.imshh.busdata.customer.domain.Customer;

public class Order {
	private String id;
	private String identify; /*订单编号*/
	private String orderType;
	private Date orderDate;  /*订单生成日期*/
	private Float amountRMB;    /*订单总金额*/
	private Float totalAmount;    /*订单总金额*/
	private String billStatus = "1";   /*订单状态*/
	
	private Customer customer;
	private OrderAppointment appointment;
	private List<OrderItem> items;  /*订单明细*/	
	
	private String remark;
	private int status = 1;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Float getAmountRMB() {
		return amountRMB;
	}
	public void setAmountRMB(Float amountRMB) {
		this.amountRMB = amountRMB;
	}
	public Float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public OrderAppointment getAppointment() {
		return appointment;
	}
	public void setAppointment(OrderAppointment appointment) {
		this.appointment = appointment;
	}
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", identify=" + identify + ", orderType=" + orderType + ", orderDate=" + orderDate
				+ ", amountRMB=" + amountRMB + ", totalAmount=" + totalAmount + ", billStatus=" + billStatus
				+ ", customer=" + customer + ", appointment=" + appointment + ", items=" + items + ", remark=" + remark
				+ ", status=" + status + "]";
	}	
}
