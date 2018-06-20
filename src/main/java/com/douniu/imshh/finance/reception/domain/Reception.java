package com.douniu.imshh.finance.reception.domain;

import com.douniu.imshh.common.BaseQO;

public class Reception extends BaseQO{
	private String customerName;
	private String orderIdentify;
	private float reception;
	private float payment;
	private boolean settlement;
	
	
	public boolean isSettlement() {
		return settlement;
	}
	public void setSettlement(boolean settlement) {
		this.settlement = settlement;
	}
	public String getOrderIdentify() {
		return orderIdentify;
	}
	public void setOrderIdentify(String orderIdentify) {
		this.orderIdentify = orderIdentify;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public float getReception() {
		return reception;
	}
	public void setReception(float reception) {
		this.reception = reception;
	}
	public float getPayment() {
		return payment;
	}
	public void setPayment(float payment) {
		this.payment = payment;
	}
	@Override
	public String toString() {
		return "Reception [customerName=" + customerName + ", orderIdentify=" + orderIdentify 
				+ ", reception=" + reception + ", payment=" + payment + "]";
	}
	
}
