package com.douniu.imshh.finance.purchase.domain;

import com.douniu.imshh.common.BaseQO;

public class PurchaseDetail extends BaseQO{
	private String id;
	private String planId;
	private String supplierId;          /*供应商ID*/
	private String supplierName;        /*供应商*/
	private String materialName;        /*原材料名称*/
	private String materialId;          /*原材料ID*/
	private String specification;       /*规格*/
	private String unit;                /*单位*/
	private int amount;               /*数量*/
	private int deliverAmount;          /*交付数*/
	private float unitPrice;            /*单价*/
	private float totlemnt;             /*合计*/
	private float paid;                 /*已支付*/
	private float balance;              /*余额*/
	
	private int status = 1;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPurchasesupplierId() {
		return supplierId;
	}
	public void setPurchasesupplierId(String purchasesupplierId) {
		this.supplierId = purchasesupplierId;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public float getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}
	public float getTotlemnt() {
		return totlemnt;
	}
	public void setTotlemnt(float totlemnt) {
		this.totlemnt = totlemnt;
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
	public float getPaid() {
		return paid;
	}
	public void setPaid(float paid) {
		this.paid = paid;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getDeliverAmount() {
		return deliverAmount;
	}
	public void setDeliverAmount(int deliverAmount) {
		this.deliverAmount = deliverAmount;
	}
	
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	@Override
	public String toString() {
		return "PurchaseDetail [id=" + id + ", planId=" + planId + ", supplierId=" + supplierId + ", supplierName="
				+ supplierName + ", materialName=" + materialName + ", materialId=" + materialId + ", specification="
				+ specification + ", unit=" + unit + ", amount=" + amount + ", deliverAmount=" + deliverAmount
				+ ", unitPrice=" + unitPrice + ", totlemnt=" + totlemnt + ", paid=" + paid + ", balance=" + balance
				+ ", status=" + status + "]";
	}
	
}
