package com.douniu.imshh.finance.payment.domain;

import com.douniu.imshh.common.BaseQO;

public class Payment extends BaseQO{
	private String supplierId;
	private String supplierName;
	private String month;
	private float debt;
	private float payment;
	private float lastDebt;
	private float lastPayment;
	private float currentDebt;
	private float currentPayment;
	
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
	
	public float getLastDebt() {
		return lastDebt;
	}
	public void setLastDebt(float lastDebt) {
		this.lastDebt = lastDebt;
	}
	public float getLastPayment() {
		return lastPayment;
	}
	public void setLastPayment(float lastPayment) {
		this.lastPayment = lastPayment;
	}
	
	public float getCurrentDebt() {
		return currentDebt;
	}
	public void setCurrentDebt(float currentDebt) {
		this.currentDebt = currentDebt;
	}
	public float getCurrentPayment() {
		return currentPayment;
	}
	public void setCurrentPayment(float currentPayment) {
		this.currentPayment = currentPayment;
	}
	@Override
	public String toString() {
		return "Payment [supplierId=" + supplierId + ", supplierName=" + supplierName + ", month=" + month + ", debt="
				+ debt + ", payment=" + payment + ", lastDebt=" + lastDebt + ", lastPayment=" + lastPayment
				+ ", currentDebt=" + currentDebt + ", currentPayment=" + currentPayment + "]";
	}
	
}
