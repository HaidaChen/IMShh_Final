package com.douniu.imshh.busdata.material.domain;

import com.douniu.imshh.common.BaseQO;

public class Material extends BaseQO{
	private String id;
	private String name;
	private String unit;
	private String specification;
	private String category;
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
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
		return "Material [id=" + id + ", name=" + name + ", unit=" + unit + ", specification=" + specification
				+ ", category=" + category + ", remark=" + remark + ", status=" + status + "]";
	}
}
