package com.douniu.imshh.product.domain;

import java.util.Date;

public class ProductOutTableRow {
	private String id;
	private String number;      /*单号*/
	private Date billDate;      /*发生日期*/
	private String billReason;
	private String preparedBy;   /*制单人*/	
	private String auditor;      /*审核人*/
	private String custodian;    /*保管*/
	private Product product;
	private int quantity;
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
	public String getBillReason() {
		return billReason;
	}
	public void setBillReason(String billReason) {
		this.billReason = billReason;
	}
	public String getPreparedBy() {
		return preparedBy;
	}
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public String getCustodian() {
		return custodian;
	}
	public void setCustodian(String custodian) {
		this.custodian = custodian;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
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
		return "ProductInTableRow [id=" + id + ", number=" + number + ", billDate=" + billDate + ", billReason="
				+ billReason + ", preparedBy=" + preparedBy + ", auditor=" + auditor + ", custodian=" + custodian
				+ ", product=" + product + ", quantity=" + quantity + ", remark=" + remark + "]";
	}
	
	public ProductOutTableRow(){super();}
	
	public ProductOutTableRow(ProductOutBill bill, BillDetail detail){
		this.id = bill.getId();
		this.number = bill.getNumber();
		this.billDate = bill.getBillDate();
		this.billReason = bill.getBillReason();
		this.preparedBy = bill.getPreparedBy(); 
		this.auditor = bill.getAuditor();     
		this.custodian = bill.getCustodian(); 
		this.product = detail.getProduct();
		this.quantity = detail.getQuantity();
		this.remark = detail.getRemark();
	}
}
