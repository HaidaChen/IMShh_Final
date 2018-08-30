package com.douniu.imshh.finance.dashboard.domain;

public class ProductIndicators {
	private float orderAmount;
	private float deliverAmount;
	private int orderQuantity;
	private int deliverQuantity;
	private String year;
	public float getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(float orderAmount) {
		this.orderAmount = orderAmount;
	}
	public float getDeliverAmount() {
		return deliverAmount;
	}
	public void setDeliverAmount(float deliverAmount) {
		this.deliverAmount = deliverAmount;
	}
	public int getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public int getDeliverQuantity() {
		return deliverQuantity;
	}
	public void setDeliverQuantity(int deliverQuantity) {
		this.deliverQuantity = deliverQuantity;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	@Override
	public String toString() {
		return "ProductIndicators [orderAmount=" + orderAmount + ", deliverAmount=" + deliverAmount + ", orderQuantity="
				+ orderQuantity + ", deliverQuantity=" + deliverQuantity + ", year=" + year + "]";
	}
	
	
}
