package com.douniu.imshh.material.domain;

import java.util.Date;


public class MaterialOutBill extends MaterialBill {
	private String id;
	private Date outDate;         /*发生日期*/
	private String preparedBy;   /*制单人*/	
	private String auditor;      /*审核人*/
	private String custodian;    /*保管*/
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
		return "MaterialOutBill [outDate=" + outDate + ", preparedBy=" + preparedBy + ", auditor=" + auditor
				+ ", custodian=" + custodian + ", billStatus=" + billStatus + ", remark="
				+ remark + ", status=" + status + "]";
	}
	
	
}
