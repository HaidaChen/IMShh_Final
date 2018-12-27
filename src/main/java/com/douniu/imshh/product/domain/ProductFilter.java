package com.douniu.imshh.product.domain;

import com.douniu.imshh.common.BaseQO;

public class ProductFilter extends BaseQO{
	private String number;
	private String billReason;
	private String pdtId;
	private String code;
	private String startPeriod;
	private String endPeriod;
	private int billStatus = -1;
	private int profitLoss;
	private int ignore0storage;
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getBillReason() {
		return billReason;
	}
	public void setBillReason(String billReason) {
		this.billReason = billReason;
	}
	public String getPdtId() {
		return pdtId;
	}
	public void setPdtId(String pdtId) {
		this.pdtId = pdtId;
	}
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(int billStatus) {
		this.billStatus = billStatus;
	}
	public int getProfitLoss() {
		return profitLoss;
	}
	public void setProfitLoss(int profitLoss) {
		this.profitLoss = profitLoss;
	}
	public int getIgnore0storage() {
		return ignore0storage;
	}
	public void setIgnore0storage(int ignore0storage) {
		this.ignore0storage = ignore0storage;
	}
	public String getStartPeriod() {
		return startPeriod;
	}
	public void setStartPeriod(String startPeriod) {
		this.startPeriod = startPeriod;
	}
	public String getEndPeriod() {
		return endPeriod;
	}
	public void setEndPeriod(String endPeriod) {
		this.endPeriod = endPeriod;
	}
	@Override
	public String toString() {
		return "ProductFilter [number=" + number + ", billReason=" + billReason + ", pdtId=" + pdtId + ", code=" + code
				+ ", startPeriod=" + startPeriod + ", endPeriod=" + endPeriod + ", billStatus=" + billStatus
				+ ", profitLoss=" + profitLoss + ", ignore0storage=" + ignore0storage + "]";
	}
	
}
