package com.douniu.imshh.finance.account.domain;

public class Payment {
	private String supplierId;
	private String supplierName;
	private String month;
	private float debt;
	private float payment;
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
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public float getDebt() {
		return debt;
	}
	public void setDebt(float debt) {
		this.debt = debt;
	}
	public float getPayment() {
		return payment;
	}
	public void setPayment(float payment) {
		this.payment = payment;
	}
	@Override
	public String toString() {
		return "Payment [supplierId=" + supplierId + ", supplierName=" + supplierName + ", month=" + month + ", debt="
				+ debt + ", payment=" + payment + "]";
	}	
}
