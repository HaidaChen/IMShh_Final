package com.douniu.imshh.product.domain;

import java.util.HashMap;
import java.util.Map;

import com.douniu.imshh.busdata.product.domain.Product;

public class InventoryMap {
	private String pdtId;
	private String pdtName;
	private String pdtCode;
	private String pdtModel;
	
	private Map<String, Integer> storageMap = new HashMap<>(); 

	public InventoryMap(){super();}

	public InventoryMap(Product product){
		this.pdtId = product.getId();
		this.pdtName = product.getName();
		this.pdtCode = product.getCode();
		this.pdtModel = product.getModel();
	}

	public String getPdtId() {
		return pdtId;
	}

	public void setPdtId(String pdtId) {
		this.pdtId = pdtId;
	}

	public String getPdtName() {
		return pdtName;
	}

	public void setPdtName(String pdtName) {
		this.pdtName = pdtName;
	}

	public String getPdtCode() {
		return pdtCode;
	}

	public void setPdtCode(String pdtCode) {
		this.pdtCode = pdtCode;
	}

	public String getPdtModel() {
		return pdtModel;
	}

	public void setPdtModel(String pdtModel) {
		this.pdtModel = pdtModel;
	}

	public Map<String, Integer> getStorageMap() {
		return storageMap;
	}

	public void setStorageMap(Map<String, Integer> storageMap) {
		this.storageMap = storageMap;
	}

	@Override
	public String toString() {
		return "InventoryMap [pdtId=" + pdtId + ", pdtName=" + pdtName + ", pdtCode=" + pdtCode + ", pdtModel="
				+ pdtModel + ", storageMap=" + storageMap + "]";
	}

}
