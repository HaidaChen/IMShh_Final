package com.douniu.imshh.material.domain;

public class InventoryDetail {
	private String id;
	private String materialId;
	private float expectAmount;
	private float actualAmount;
	private float price;
	private float total;
	
	private String remark;
	private int status = 1;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	public float getExpectAmount() {
		return expectAmount;
	}
	public void setExpectAmount(float expectAmount) {
		this.expectAmount = expectAmount;
	}
	public float getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(float actualAmount) {
		this.actualAmount = actualAmount;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
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
		return "InventoryDetail [id=" + id + ", materialId=" + materialId + ", expectAmount=" + expectAmount
				+ ", actualAmount=" + actualAmount + ", price=" + price + ", total=" + total + ", remark=" + remark
				+ ", status=" + status + "]";
	}
	
	
}
