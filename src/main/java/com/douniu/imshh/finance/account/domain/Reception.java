package com.douniu.imshh.finance.account.domain;

public class Reception {
	private String customerId;
	private String customerName;
	private String month;
	private float reception;
	private float payment;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
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
		return "Reception [customerId=" + customerId + ", customerName=" + customerName + ", month=" + month
				+ ", reception=" + reception + ", payment=" + payment + "]";
	}
	
	
}
