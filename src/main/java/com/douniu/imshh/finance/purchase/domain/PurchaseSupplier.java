package com.douniu.imshh.finance.purchase.domain;

/**
 * @author Administrator
 * 供应商视图，为方便根据供应商查看欠款情况
 */
public class PurchaseSupplier {
	private String id;
	private String supplierId;          /*供应商ID*/
	private String supplierName;        /*供应商名称*/
	private String planId;              /*关联采购计划*/
	private String planIdentify;        /*关联采购计划*/
	private float amount;               /*金额*/
	private float paid;                 /*已支付*/
	private float balance;              /*余额*/
	
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
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
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
	
	
}
