package com.douniu.imshh.busdata.product.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.douniu.imshh.common.BaseQO;

public class Product extends BaseQO{
	private String id;
	private String code;
	private String name;
	private String specification;
	private String model;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date lineDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date downlineDate;
	private String remark;
	private int status = 1;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Date getLineDate() {
		return lineDate;
	}
	public void setLineDate(Date lineDate) {
		this.lineDate = lineDate;
	}
	
	public Date getDownlineDate() {
		return downlineDate;
	}
	public void setDownlineDate(Date downlineDate) {
		this.downlineDate = downlineDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", code=" + code + ", name=" + name + ", specification=" + specification
				+ ", model=" + model + ", remark=" + remark + ", lineDate=" + lineDate + ", downlineDate="
				+ downlineDate + ", status=" + status + "]";
	}	
}
