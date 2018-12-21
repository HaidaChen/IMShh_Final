package com.douniu.imshh.material.domain;

import java.util.Date;

public class MaterialInOut {
	private String id;	
	private Date genDate;
	private String billPeriod;
	private float inQuantity;
	private float inAmount;
	private float outQuantity;
	private float outAmount;
	private float balanceQuantity;
	private float balanceAmount;
	private String summary;
	private MaterialBill bill;
	private Material material;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public MaterialBill getBill() {
		return bill;
	}
	public void setBill(MaterialBill bill) {
		this.bill = bill;
	}
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	public Date getGenDate() {
		return genDate;
	}
	public void setGenDate(Date genDate) {
		this.genDate = genDate;
	}
	public String getBillPeriod() {
		return billPeriod;
	}
	public void setBillPeriod(String billPeriod) {
		this.billPeriod = billPeriod;
	}
	public float getInQuantity() {
		return inQuantity;
	}
	public void setInQuantity(float inQuantity) {
		this.inQuantity = inQuantity;
	}
	public float getInAmount() {
		return inAmount;
	}
	public void setInAmount(float inAmount) {
		this.inAmount = inAmount;
	}
	public float getOutQuantity() {
		return outQuantity;
	}
	public void setOutQuantity(float outQuantity) {
		this.outQuantity = outQuantity;
	}
	public float getOutAmount() {
		return outAmount;
	}
	public void setOutAmount(float outAmount) {
		this.outAmount = outAmount;
	}
	public float getBalanceQuantity() {
		return balanceQuantity;
	}
	public void setBalanceQuantity(float balanceQuantity) {
		this.balanceQuantity = balanceQuantity;
	}
	public float getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(float balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@Override
	public String toString() {
		return "InOutDetail [id=" + id + ", bill=" + bill + ", material=" + material + ", genDate=" + genDate
				+ ", inQuantity=" + inQuantity + ", inAmount=" + inAmount + ", outQuantity=" + outQuantity
				+ ", outAmount=" + outAmount + ", balanceQuantity=" + balanceQuantity + ", balanceAmount="
				+ balanceAmount + ", summary=" + summary + "]";
	}	
	
	@Override
	public boolean equals(Object target){
		if (target instanceof MaterialInOut){
			MaterialInOut _targe = (MaterialInOut)target;
			return this.material.getId().equals(_targe.getMaterial().getId());
		}
		
		if (target instanceof MaterialInOutMap){
			MaterialInOutMap _target = (MaterialInOutMap)target;
			return this.material.getId().equals(_target.getMtlId());
		}
			
		
		return false;
	}

}
