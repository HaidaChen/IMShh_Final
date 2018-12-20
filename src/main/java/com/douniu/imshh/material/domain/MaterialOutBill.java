package com.douniu.imshh.material.domain;

import java.util.Date;
import java.util.List;


public class MaterialOutBill {
	private String id;
	private Date outDate;         /*发生日期*/
	private String number;       /*出库单号*/	
	private String preparedBy;   /*制单人*/	
	private String auditor;      /*审核人*/
	private String custodian;    /*保管*/
	private String outReason;       /*出库类型：生产出库、退货出库、等*/
	private List<BillDetail> details;
	private float totalQuantity; /*合计数量*/
	private float totalAmount;   /*合计金额*/
	private int billStatus = 0;  /*单据状态：0：未审批；1：已审批*/
	
	private String remark;
	private int status = 1;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getOutDate() {
		return outDate;
	}
	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
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
	public String getOutReason() {
		return outReason;
	}
	public void setOutReason(String outReason) {
		this.outReason = outReason;
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
	public int getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(int billStatus) {
		this.billStatus = billStatus;
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
	
	@Override
	public String toString() {
		return "MaterialOutBill [id=" + id + ", outDate=" + outDate + ", number=" + number + ", preparedBy="
				+ preparedBy + ", auditor=" + auditor + ", custodian=" + custodian + ", outReason=" + outReason
				+ ", details=" + details + ", totalQuantity=" + totalQuantity + ", totalAmount=" + totalAmount
				+ ", billStatus=" + billStatus + ", remark=" + remark + ", status=" + status + "]";
	}
	
	
}
