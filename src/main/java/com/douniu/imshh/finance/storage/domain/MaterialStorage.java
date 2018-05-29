package com.douniu.imshh.finance.storage.domain;

import com.douniu.imshh.common.BaseQO;

public class MaterialStorage extends BaseQO{
	private String materialName;
	private String specification1;      /*规格1*/
	private String specification2;      /*规格2*/
	private String specification3;      /*规格3*/
	private int storage;
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
	public int getStorage() {
		return storage;
	}
	public void setStorage(int storage) {
		this.storage = storage;
	}
	@Override
	public String toString() {
		return "MaterialStorage [materialName=" + materialName + ", specification1=" + specification1
				+ ", specification2=" + specification2 + ", specification3=" + specification3 + ", storage=" + storage
				+ "]";
	}
	
	
}
