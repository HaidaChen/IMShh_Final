package com.douniu.imshh.material.domain;

import com.douniu.imshh.common.BaseQO;

public class MaterialFilter extends BaseQO{
	private String supplierName;
	private String category;
	private String name;	
		
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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
	
	@Override
	public String toString() {
		return "Fliter [supplierName=" + supplierName + ", category=" + category + ", name=" + name + "]";
	}
	
}
