package com.douniu.imshh.material.domain;

import java.util.HashMap;
import java.util.Map;

public class InventoryMap {
	private String mtlId;
	private String mtlName;
	private String mtlSpec;
	private String mtlUnit;
	
	private Map<String, Float> storageMap = new HashMap<>(); 

	public InventoryMap(){super();}

	public InventoryMap(Material material){
		this.mtlId = material.getId();
		this.mtlName = material.getName();
		this.mtlSpec = material.getSpecification();
		this.mtlUnit = material.getUnit();
	}

	public String getMtlId() {
		return mtlId;
	}

	public void setMtlId(String mtlId) {
		this.mtlId = mtlId;
	}

	public String getMtlName() {
		return mtlName;
	}

	public void setMtlName(String mtlName) {
		this.mtlName = mtlName;
	}

	public String getMtlSpec() {
		return mtlSpec;
	}

	public void setMtlSpec(String mtlSpec) {
		this.mtlSpec = mtlSpec;
	}

	public String getMtlUnit() {
		return mtlUnit;
	}

	public void setMtlUnit(String mtlUnit) {
		this.mtlUnit = mtlUnit;
	}

	public Map<String, Float> getStorageMap() {
		return storageMap;
	}

	public void setStorageMap(Map<String, Float> storageMap) {
		this.storageMap = storageMap;
	}

	@Override
	public String toString() {
		return "InventoryMap [mtlId=" + mtlId + ", mtlName=" + mtlName + ", mtlSpec=" + mtlSpec + ", mtlUnit=" + mtlUnit
				+ ", storageMap=" + storageMap + "]";
	}
	
	
}
