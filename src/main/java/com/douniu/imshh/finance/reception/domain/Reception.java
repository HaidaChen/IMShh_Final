package com.douniu.imshh.finance.reception.domain;

import com.douniu.imshh.common.BaseQO;

public class Reception extends BaseQO{
	private String customerName;
	private String orderIdentify;
	private String month;
	private float debt;
	private float reception;
	private float lastDebt;
	private float currentDebt;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getOrderIdentify() {
		return orderIdentify;
	}
	public void setOrderIdentify(String orderIdentify) {
		this.orderIdentify = orderIdentify;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public float getDebt() {
		return debt;
	}
	public void setDebt(float debt) {
		this.debt = debt;
	}
	public float getReception() {
		return reception;
	}
	public void setReception(float reception) {
		this.reception = reception;
	}
	public float getLastDebt() {
		return lastDebt;
	}
	public void setLastDebt(float lastDebt) {
		this.lastDebt = lastDebt;
	}
	public float getCurrentDebt() {
		return currentDebt;
	}
	public void setCurrentDebt(float currentDebt) {
		this.currentDebt = currentDebt;
	}
	@Override
	public String toString() {
		return "Reception [customerName=" + customerName + ", orderIdentify=" + orderIdentify + ", month=" + month
				+ ", debt=" + debt + ", reception=" + reception + ", lastDebt=" + lastDebt + ", currentDebt="
				+ currentDebt + "]";
	}	
}
