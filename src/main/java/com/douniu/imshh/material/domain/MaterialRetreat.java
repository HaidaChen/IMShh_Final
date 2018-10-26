package com.douniu.imshh.material.domain;

public class MaterialRetreat {
	private String id;
	private String materialInId;
	private float amount;
	
	private String remark;
	private int status = 1;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMaterialInId() {
		return materialInId;
	}
	public void setMaterialInId(String materialInId) {
		this.materialInId = materialInId;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
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
		return "MaterialRetreat [id=" + id + ", materialInId=" + materialInId + ", amount=" + amount + ", remark="
				+ remark + ", status=" + status + "]";
	}
	
	
}
