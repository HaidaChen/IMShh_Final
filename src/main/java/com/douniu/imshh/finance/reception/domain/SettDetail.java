package com.douniu.imshh.finance.reception.domain;

import java.util.Date;

public class SettDetail {
	private String settlementId;
	private String orderIdentify;
	private String custName;
	private Date orderDate;
	private float amountRMB;
	private float amountDollar;
	private float paid;
	
	public String getSettlementId() {
		return settlementId;
	}
	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}
	public String getOrderIdentify() {
		return orderIdentify;
	}
	public void setOrderIdentify(String orderIdentify) {
		this.orderIdentify = orderIdentify;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public float getAmountRMB() {
		return amountRMB;
	}
	public void setAmountRMB(float amountRMB) {
		this.amountRMB = amountRMB;
	}
	public float getAmountDollar() {
		return amountDollar;
	}
	public void setAmountDollar(float amountDollar) {
		this.amountDollar = amountDollar;
	}
	public float getPaid() {
		return paid;
	}
	public void setPaid(float paid) {
		this.paid = paid;
	}
	@Override
	public String toString() {
		return "SettDetail [settlementId=" + settlementId + ", orderIdentify=" + orderIdentify + ", custName="
				+ custName + ", orderDate=" + orderDate + ", amountRMB=" + amountRMB + ", amountDollar=" + amountDollar
				+ ", paid=" + paid + "]";
	}
	
	
}
