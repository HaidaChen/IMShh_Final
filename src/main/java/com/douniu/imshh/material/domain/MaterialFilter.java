package com.douniu.imshh.material.domain;

import com.douniu.imshh.common.BaseQO;

public class MaterialFilter extends BaseQO{
	
	
	private String name;
	private String specification;
	private String category;
	private String ctgCode;
	private String lowerStorage;
	private String upperStorage;
	private String remark;
	private String supplier;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	@Override
	public String toString() {
		return "Fliter [supplier=" + supplier + ", category=" + category + ", name=" + name + "]";
	}
	
}
