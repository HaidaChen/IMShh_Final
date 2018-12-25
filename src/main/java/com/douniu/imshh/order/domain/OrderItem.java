package com.douniu.imshh.order.domain;

import com.douniu.imshh.busdata.product.domain.Product;

public class OrderItem {
	private String id;
	private String orderId;
	private Product product;
	private int quantity;    /*产品数量*/
	private float price;  /*人民币单价*/
	private float amount;    /*合计*/
	
	private String remark;
	private int status = 1;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
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
		return "OrderItem [id=" + id + ", orderId=" + orderId + ", product=" + product + ", quantity=" + quantity
				+ ", price=" + price + ", amount=" + amount + ", remark=" + remark + ", status=" + status + "]";
	}
}
