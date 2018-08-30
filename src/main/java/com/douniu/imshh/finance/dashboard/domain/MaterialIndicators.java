package com.douniu.imshh.finance.dashboard.domain;

public class MaterialIndicators {
	private float orderAmount;
	private int orderQuantity;
	private int useQuantity;
	private String year;
	
	public float getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(float orderAmount) {
		this.orderAmount = orderAmount;
	}
	public int getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public int getUseQuantity() {
		return useQuantity;
	}
	public void setUseQuantity(int useQuantity) {
		this.useQuantity = useQuantity;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	@Override
	public String toString() {
		return "MaterialIndicators [orderAmount=" + orderAmount + ", orderQuantity=" + orderQuantity + ", useQuantity="
				+ useQuantity + ", year=" + year + "]";
	}	
}
