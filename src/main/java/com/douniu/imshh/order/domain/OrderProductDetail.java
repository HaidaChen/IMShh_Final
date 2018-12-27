package com.douniu.imshh.order.domain;

import com.douniu.imshh.busdata.product.domain.Product;

public class OrderProductDetail {
	private String id;
	private Product product;
	private int orderQuantity;
	private float orderAmount;
	private int inQuantity;
	private int outQuantity;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public float getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(float orderAmount) {
		this.orderAmount = orderAmount;
	}
	public int getInQuantity() {
		return inQuantity;
	}
	public void setInQuantity(int inQuantity) {
		this.inQuantity = inQuantity;
	}
	public int getOutQuantity() {
		return outQuantity;
	}
	public void setOutQuantity(int outQuantity) {
		this.outQuantity = outQuantity;
	}
	@Override
	public String toString() {
		return "OrderProductDetail [product=" + product + ", orderQuantity=" + orderQuantity + ", inQuantity="
				+ inQuantity + ", outQuantity=" + outQuantity + "]";
	}
	
	
}
