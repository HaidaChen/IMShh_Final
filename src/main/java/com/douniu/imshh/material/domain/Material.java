package com.douniu.imshh.material.domain;

import java.util.List;

public class Material {
	private String id;
	private String name;
	private String category;
	private String specification1;
	private String specification2;
	private String specification3;
	private String unit;
	private String formula;
	private String remark;
	private int status = 1;
	
	private float storage;
	private List<HistorySupplier> hisSuppliers;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSpecification1() {
		return specification1;
	}
	public void setSpecification1(String specification1) {
		this.specification1 = specification1;
	}
	public String getSpecification2() {
		return specification2;
	}
	public void setSpecification2(String specification2) {
		this.specification2 = specification2;
	}
	public String getSpecification3() {
		return specification3;
	}
	public void setSpecification3(String specification3) {
		this.specification3 = specification3;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
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
	public float getStorage() {
		return storage;
	}
	public void setStorage(float storage) {
		this.storage = storage;
	}
	public List<HistorySupplier> getHisSuppliers() {
		return hisSuppliers;
	}
	public void setHisSuppliers(List<HistorySupplier> hisSuppliers) {
		this.hisSuppliers = hisSuppliers;
	}
		
	@Override
	public String toString() {
		return "Material [id=" + id + ", name=" + name + ", category=" + category + ", specification1=" + specification1
				+ ", specification2=" + specification2 + ", specification3=" + specification3 + ", unit=" + unit
				+ ", formula=" + formula + ", remark=" + remark + ", status=" + status + ", storage=" + storage
				+ ", hisSuppliers=" + hisSuppliers + "]";
	}	
}
