package com.douniu.imshh.material.domain;

public class Material{
	private String id;
	private String name;
	private String category;
	private String specification1;
	private String specification2;
	private String specification3;
	private String unit;
	private String remark;
	private int status = 1;

	private float storage;
	private String supplierNames;
	
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
		
	public String getSupplierNames() {
		return supplierNames;
	}
	public void setSupplierNames(String supplierNames) {
		this.supplierNames = supplierNames;
	}
	
	@Override
	public String toString() {
		return "Material [id=" + id + ", name=" + name + ", category=" + category + ", specification1=" + specification1
				+ ", specification2=" + specification2 + ", specification3=" + specification3 + ", unit=" + unit
				+ ", remark=" + remark + ", status=" + status + ", storage=" + storage
				+ ", supplierNames=" + supplierNames + "]";
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return this.name.equals(material.name) &&
				this.specification1.equals(material.specification1) &&
				this.specification2.equals(material.specification2) &&
				this.specification3.equals(material.specification3);
    }	
}
