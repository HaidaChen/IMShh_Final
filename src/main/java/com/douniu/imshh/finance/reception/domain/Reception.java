package com.douniu.imshh.finance.reception.domain;

import java.util.Date;

import com.douniu.imshh.common.BaseQO;

public class Reception extends BaseQO{
	private String id;
	
	private Date receiveDate;           /*交付日期*/
	private String orderIdentify;       /*关联订单*/
	private String supplierId;          /*供应商ID*/
	private String supplierName;        /*供应商*/
	private String materialId;          /*原材料ID*/
	private String materialName;        /*原材料名称*/	
	private String specification1;      /*规格1*/
	private String specification2;      /*规格2*/
	private String specification3;      /*规格3*/
	private String formula;             /*计量公式*/	
	private String unit;                /*计量单位*/
	private float meterage = 1;         /*计量数*/
	private float unitPrice;            /*单价*/	
	private int amount;                 /*交付数量*/
	private float totlemnt;             /*合计*/	
	
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
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public float getMeterage() {
		return meterage;
	}
	public void setMeterage(float meterage) {
		this.meterage = meterage;
	}
	public float getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public float getTotlemnt() {
		return totlemnt;
	}
	public void setTotlemnt(float totlemnt) {
		this.totlemnt = totlemnt;
	}
	public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
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
	public String getOrderIdentify() {
		return orderIdentify;
	}
	public void setOrderIdentify(String orderIdentify) {
		this.orderIdentify = orderIdentify;
	}
	@Override
	public String toString() {
		return "Reception [id=" + id + ", receiveDate=" + receiveDate + ", orderIdentify=" + orderIdentify
				+ ", supplierId=" + supplierId + ", supplierName=" + supplierName + ", materialId=" + materialId
				+ ", materialName=" + materialName + ", specification1=" + specification1 + ", specification2="
				+ specification2 + ", specification3=" + specification3 + ", formula=" + formula + ", unit=" + unit
				+ ", meterage=" + meterage + ", unitPrice=" + unitPrice + ", amount=" + amount + ", totlemnt="
				+ totlemnt + ", remark=" + remark + ", status=" + status + "]";
	}	
}
