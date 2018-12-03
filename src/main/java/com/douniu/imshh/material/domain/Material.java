package com.douniu.imshh.material.domain;

public class Material{
	private String id;
	private String name;
	private Category ctg;
	private String specification;
	private String unit;
	private String remark;
	private int status = 1;

	private float storage;
	
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
	
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
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
	
	public Category getCtg() {
		return ctg;
	}
	public void setCtg(Category ctg) {
		this.ctg = ctg;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return this.name.equals(material.name) &&
				this.specification.equals(material.specification);
    }	
}
