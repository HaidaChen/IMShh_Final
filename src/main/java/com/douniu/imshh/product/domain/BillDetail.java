package com.douniu.imshh.product.domain;

public class BillDetail {
	private String id;
	private String billId;
	private Product product;
	private int quantity;
	
	private String remark;
	private int status = 1;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	public int getQuantity() {
		return quantity;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
		return "BillDetail [id=" + id + ", billId=" + billId + ", product=" + product + ", quantity=" + quantity
				+ ", remark=" + remark + ", status=" + status + "]";
	}
	
	
}
