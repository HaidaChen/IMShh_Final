package com.douniu.imshh.finance.reception.domain;

import java.sql.Date;
import java.util.List;

import com.douniu.imshh.common.BaseQO;

public class Settlement extends BaseQO{
	private String id;
	private Date settlementDate;
	private Date lastSettlement;
	private float reception;
	private float payment;
	private String remark;
	private List<SettDetail> details;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}
	public Date getLastSettlement() {
		return lastSettlement;
	}
	public void setLastSettlement(Date lastSettlement) {
		this.lastSettlement = lastSettlement;
	}
	public float getReception() {
		return reception;
	}
	public void setReception(float reception) {
		this.reception = reception;
	}
	public float getPayment() {
		return payment;
	}
	public void setPayment(float payment) {
		this.payment = payment;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public List<SettDetail> getDetails() {
		return details;
	}
	public void setDetails(List<SettDetail> details) {
		this.details = details;
	}
	@Override
	public String toString() {
		return "Settlement [date=" + settlementDate + ", reception=" + reception + ", payment=" + payment + ", remark=" + remark
				+ "]";
	}	
	
}
