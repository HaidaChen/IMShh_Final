package com.douniu.imshh.material.domain;

import com.douniu.imshh.common.BaseQO;

public class MaterialFilter extends BaseQO{
	
	private String materialId;
	private String name;
	private String number; /*入库单编号*/
	private String specification;
	private String category;
	private String ctgCode;
	private String billPeriod;
	private String billReason;
	private String startPeriod;
	private String endPeriod;
	private String lowerStorage;
	private String upperStorage;
	private String remark;
	private String supplier;
	
	private int billStatus = -1;
	private int profitLoss;
	private int ignore0storage;
	private int ignoreNullStorage;
	
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCtgCode() {
		return ctgCode;
	}
	public void setCtgCode(String ctgCode) {
		this.ctgCode = ctgCode;
	}
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	public String getBillPeriod() {
		return billPeriod;
	}
	public void setBillPeriod(String billPeriod) {
		this.billPeriod = billPeriod;
	}
	public String getBillReason() {
		return billReason;
	}
	public void setBillReason(String billReason) {
		this.billReason = billReason;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}		
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getLowerStorage() {
		return lowerStorage;
	}
	public void setLowerStorage(String lowerStorage) {
		this.lowerStorage = lowerStorage;
	}
	public String getUpperStorage() {
		return upperStorage;
	}
	public void setUpperStorage(String upperStorage) {
		this.upperStorage = upperStorage;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	
	public int getIgnoreNullStorage() {
		return ignoreNullStorage;
	}
	public void setIgnoreNullStorage(int ignoreNullStorage) {
		this.ignoreNullStorage = ignoreNullStorage;
	}
	@Override
	public String toString() {
		return "MaterialFilter [materialId=" + materialId + ", name=" + name + ", number=" + number + ", specification="
				+ specification + ", category=" + category + ", ctgCode=" + ctgCode + ", billPeriod=" + billPeriod
				+ ", billReason=" + billReason + ", startPeriod=" + startPeriod + ", endPeriod=" + endPeriod
				+ ", lowerStorage=" + lowerStorage + ", upperStorage=" + upperStorage + ", remark=" + remark
				+ ", supplier=" + supplier + ", billStatus=" + billStatus + ", profitLoss=" + profitLoss
				+ ", ignore0storage=" + ignore0storage + ", ignoreNullStorage=" + ignoreNullStorage + "]";
	}
	
}
