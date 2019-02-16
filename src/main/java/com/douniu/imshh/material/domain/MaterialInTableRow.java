package com.douniu.imshh.material.domain;

import java.util.Date;

import com.douniu.imshh.supplier.domain.Supplier;

public class MaterialInTableRow {
	private String id;
	private String number;      /*单号*/
	private Date billDate;      /*发生日期*/
	private Supplier supplier;
	private float totalAmount;
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

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
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
		return "MaterialInTableRow [id=" + id + ", number=" + number + ", billDate=" + billDate + ", supplier="
				+ supplier + ", totalAmount=" + totalAmount + ", material=" + material + ", quantity=" + quantity
				+ ", remark=" + remark + "]";
	}

	public MaterialInTableRow(){super();}
	
	public MaterialInTableRow(MaterialInBill bill, BillDetail detail){
		this.id = bill.getId();
		this.number = bill.getNumber();
		this.billDate = bill.getBillDate();
		this.supplier = bill.getSupplier();
		this.totalAmount = bill.getTotalAmount();
		this.material = detail.getMaterial();
		this.quantity = detail.getQuantity();
		this.remark = detail.getRemark();
	}
}
