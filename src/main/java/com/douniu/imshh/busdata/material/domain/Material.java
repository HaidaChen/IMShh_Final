package com.douniu.imshh.busdata.material.domain;

import com.douniu.imshh.common.BaseQO;

public class Material extends BaseQO{
	private String id;
	private String name;
	private String unit;
	private String specification1;
	private String specification2;
	private String specification3;
	private String formula;
	private String remark;
	private int status = 1;
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
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
	@Override
	public String toString() {
		return "Material [id=" + id + ", name=" + name + ", unit=" + unit + ", specification1=" + specification1
				+ ", specification2=" + specification2 + ", specification3=" + specification3 + ", formula=" + formula
				+ ", remark=" + remark + ", status=" + status + "]";
	}	
}
