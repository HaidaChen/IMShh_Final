package com.douniu.imshh.finance.storage.domain;

import java.util.Date;

import com.douniu.imshh.common.BaseQO;

public class MaterialOut extends BaseQO{
	private String id;
	
	private Date outDate;               /*出库日期*/
	private String handMan;             /*经手人*/
	private String orderIdentify;       /*关联订单*/
	private String materialId;          /*原材料ID*/
	private String materialName;        /*原材料名称*/	
	private String specification1;      /*规格1*/
	private String specification2;      /*规格2*/
	private String specification3;      /*规格3*/
	private int outAmount;              /*出库数量*/
	private int returnAmount;           /*退回数量*/
	
	private String remark;
	private int status = 1;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
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
	
	public int getOutAmount() {
		return outAmount;
	}
	public void setOutAmount(int outAmount) {
		this.outAmount = outAmount;
	}
	public int getReturnAmount() {
		return returnAmount;
	}
	public void setReturnAmount(int returnAmount) {
		this.returnAmount = returnAmount;
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
	public String getOrderIdentify() {
		return orderIdentify;
	}
	public void setOrderIdentify(String orderIdentify) {
		this.orderIdentify = orderIdentify;
	}
	public Date getOutDate() {
		return outDate;
	}
	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
	public String getHandMan() {
		return handMan;
	}
	public void setHandMan(String handMan) {
		this.handMan = handMan;
	}
	@Override
	public String toString() {
		return "MaterialOut [id=" + id + ", outDate=" + outDate + ", handMan=" + handMan + ", orderIdentify="
				+ orderIdentify + ", materialId=" + materialId + ", materialName=" + materialName + ", specification1="
				+ specification1 + ", specification2=" + specification2 + ", specification3=" + specification3
				+ ", outAmount=" + outAmount + ", returnAmount=" + returnAmount + ", remark=" + remark + ", status="
				+ status + "]";
	}
	
}
