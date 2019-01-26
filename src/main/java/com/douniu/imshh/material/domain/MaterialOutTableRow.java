package com.douniu.imshh.material.domain;

import java.util.Date;

public class MaterialOutTableRow {
	private String id;
	private String number;      /*单号*/
	private Date billDate;      /*发生日期*/
	private Material material;
	private float quantity;
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "MaterialOutTableRow [id=" + id + ", number=" + number + ", billDate=" + billDate + ", material="
				+ material + ", quantity=" + quantity + ", remark=" + remark + "]";
	}
	
	public MaterialOutTableRow(){super();}
	
	public MaterialOutTableRow(MaterialOutBill bill, BillDetail detail){
		this.id = bill.getId();
		this.number = bill.getNumber();
		this.billDate = bill.getBillDate();
		this.material = detail.getMaterial();
		this.quantity = detail.getQuantity();
		this.remark = detail.getRemark();
	}
}
