package com.douniu.imshh.material.domain;

import java.util.Date;
import java.util.List;

public class MaterialBill {
	private String id;
	private String number;      /*单号*/
	private Date billDate;      /*发生日期*/
	private String billReason;
	private List<BillDetail> details;  /*分录*/
	private float totalQuantity; /*合计数量*/
	private float totalAmount;   /*合计金额*/
	
	
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
	public String getBillReason() {
		return billReason;
	}
	public void setBillReason(String billReason) {
		this.billReason = billReason;
	}
	public List<BillDetail> getDetails() {
		return details;
	}
	public void setDetails(List<BillDetail> details) {
		this.details = details;
	}
	public float getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(float totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@Override
	public String toString() {
		return "Bill [id=" + id + ", number=" + number + ", billDate=" + billDate + ", billReason=" + billReason
				+ ", details=" + details + ", totalQuantity=" + totalQuantity + ", totalAmount=" + totalAmount + "]";
	}
}
