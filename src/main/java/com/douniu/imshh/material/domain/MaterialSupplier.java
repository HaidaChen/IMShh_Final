package com.douniu.imshh.material.domain;

public class MaterialSupplier {
	private String id;
	private String name;
	private String categoryIds;
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
	public String getCategoryIds() {
		return categoryIds;
	}
	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterialSupplier supplier = (MaterialSupplier) o;
        return this.name.equals(supplier.name);
    }	
	@Override
	public String toString() {
		return "MaterialSupplier [id=" + id + ", name=" + name + ", categoryIds=" + categoryIds + ", remark=" + remark
				+ ", status=" + status + "]";
	}
}
