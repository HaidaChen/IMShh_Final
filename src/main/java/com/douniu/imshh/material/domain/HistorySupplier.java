package com.douniu.imshh.material.domain;

import java.util.List;

public class HistorySupplier {
	private String id;
	private String materialId;
	private String materialName;
	private String supplierId;
	private String supplierName;
	private String remark;
	private int status = 1;
	private List<HistoryPrice> historyPrices;
	
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
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public List<HistoryPrice> getHistoryPrices() {
		return historyPrices;
	}
	public void setHistoryPrices(List<HistoryPrice> historyPrices) {
		this.historyPrices = historyPrices;
	}
	
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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
		return "HistorySupplier [id=" + id + ", materialId=" + materialId + ", materialName=" + materialName
				+ ", supplierId=" + supplierId + ", supplierName=" + supplierName + ", remark=" + remark + ", status="
				+ status + ", historyPrices=" + historyPrices + "]";
	}
	
}
