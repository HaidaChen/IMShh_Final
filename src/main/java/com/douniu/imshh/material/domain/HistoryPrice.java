package com.douniu.imshh.material.domain;

import java.util.Date;

public class HistoryPrice {
	private String id;
	private String supplierId;
	private float price;
	private Date markDate;
	
	private String remark;
	private int status = 1;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Date getMarkDate() {
		return markDate;
	}
	public void setMarkDate(Date date) {
		this.markDate = date;
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
		return "HistoryPrice [id=" + id + ", supplierId=" + supplierId + ", price=" + price + ", markDate=" + markDate
				+ ", remark=" + remark + ", status=" + status + "]";
	}
		
}
