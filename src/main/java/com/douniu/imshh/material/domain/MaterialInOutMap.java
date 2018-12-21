package com.douniu.imshh.material.domain;

import java.util.HashMap;
import java.util.Map;

public class MaterialInOutMap {
	private String mtlId;
	private String mtlName;
	private String mtlSpec;
	private float startStorage;
	private Map<String, PeriodInOut> iomap = new HashMap<>();
	private float mtlTotalIn;
	private float mtlTotalOut;
	private float storage;
	
	public MaterialInOutMap(){super();}
	
	public MaterialInOutMap(Material material){
		this.mtlId = material.getId();
		this.mtlName = material.getName();
		this.mtlSpec = material.getSpecification();
		this.storage = material.getStorage();
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

	public Map<String, PeriodInOut> getIomap() {
		return iomap;
	}

	public void setIomap(Map<String, PeriodInOut> iomap) {
		this.iomap = iomap;
	}

	public float getStartStorage() {
		return startStorage;
	}

	public void setStartStorage(float startStorage) {
		this.startStorage = startStorage;
	}

	public float getMtlTotalIn() {
		return mtlTotalIn;
	}

	public void setMtlTotalIn(float mtlTotalIn) {
		this.mtlTotalIn = mtlTotalIn;
	}

	public float getMtlTotalOut() {
		return mtlTotalOut;
	}

	public void setMtlTotalOut(float mtlTotalOut) {
		this.mtlTotalOut = mtlTotalOut;
	}

	public float getStorage() {
		return storage;
	}

	public void setStorage(float storage) {
		this.storage = storage;
	}
	
	@Override
	public String toString() {
		return "MaterialInOutMap [mtlId=" + mtlId + ", mtlName=" + mtlName + ", mtlSpec=" + mtlSpec + ", startStorage="
				+ startStorage + ", iomap=" + iomap + ", mtlTotalIn=" + mtlTotalIn + ", mtlTotalOut=" + mtlTotalOut
				+ ", storage=" + storage + "]";
	}
}
