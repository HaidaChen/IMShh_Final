package com.douniu.imshh.product.domain;

import java.util.Date;
import java.util.List;

public class Inventory {
	private String id;
	private Date inventoryDate;
	private int pricingRules;
	private float total;
	private String remark;
	private int status = 1; 
	
	private List<InventoryDetail> details;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public Date getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(Date inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	public int getPricingRules() {
		return pricingRules;
	}

	public void setPricingRules(int pricingRules) {
		this.pricingRules = pricingRules;
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

	public List<InventoryDetail> getDetails() {
		return details;
	}

	public void setDetails(List<InventoryDetail> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "Inventory [id=" + id + ", inventoryDate=" + inventoryDate + ", pricingRules=" + pricingRules
				+ ", total=" + total + ", remark=" + remark + ", status=" + status + ", details=" + details + "]";
	}

}
