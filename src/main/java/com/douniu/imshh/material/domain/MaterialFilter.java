package com.douniu.imshh.material.domain;

import com.douniu.imshh.common.BaseQO;

public class MaterialFilter extends BaseQO{
	private String supplier;
	private String category;
	private String name;
	private String code;
	private String parentId;
	private String remark;
		
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}		
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}	
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
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
