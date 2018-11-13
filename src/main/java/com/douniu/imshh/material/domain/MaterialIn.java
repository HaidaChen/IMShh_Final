package com.douniu.imshh.material.domain;

import java.util.Date;

public class MaterialIn {
	private String id;
	private Date inDate;
	private String materialId;          /*原材料ID*/
	private String materialName;        /*原材料名称*/	
	private String specification1;      /*规格1*/
	private String specification2;      /*规格2*/
	private String specification3;      /*规格3*/
	private String unit;                /*计量单位*/
	private String supplierId;          /*供应商ID*/
	private String supplierName;        /*供应商*/
	private float amount;
	
	private String remark;
	private int status = 1;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getInDate() {
		return inDate;
	}
	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getSpecification1() {
		return specification1;
	}
	public void setSpecification1(String specification1) {
		this.specification1 = specification1;
	}
	public String getSpecification2() {
		return specification2;
	}
	public void setSpecification2(String specification2) {
		this.specification2 = specification2;
	}
	public String getSpecification3() {
		return specification3;
	}
	public void setSpecification3(String specification3) {
		this.specification3 = specification3;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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
		return "MaterialIn [id=" + id + ", inDate=" + inDate + ", materialId=" + materialId + ", materialName="
				+ materialName + ", specification1=" + specification1 + ", specification2=" + specification2
				+ ", specification3=" + specification3 + ", unit=" + unit + ", supplierId=" + supplierId
				+ ", supplierName=" + supplierName + ", amount=" + amount + ", remark=" + remark + ", status=" + status
				+ "]";
	}
}
