package com.douniu.imshh.product.domain;

import java.util.HashMap;
import java.util.Map;

import com.douniu.imshh.busdata.product.domain.Product;

public class ProductInOutMap {
	private String pdtId;
	private String pdtCode;
	private String pdtModel;
	private float startStorage;
	private Map<String, PeriodInOut> iomap = new HashMap<>();
	private float mtlTotalIn;
	private float mtlTotalOut;
	private float storage;
	
	public ProductInOutMap(){super();}
	
	public ProductInOutMap(Product product){
		this.pdtId = product.getId();
		this.pdtCode = product.getCode();
		this.pdtModel = product.getModel();
		this.storage = product.getStorage();
	}

	public String getPdtId() {
		return pdtId;
	}

	public void setPdtId(String pdtId) {
		this.pdtId = pdtId;
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

	public float getStartStorage() {
		return startStorage;
	}

	public void setStartStorage(float startStorage) {
		this.startStorage = startStorage;
	}

	public Map<String, PeriodInOut> getIomap() {
		return iomap;
	}

	public void setIomap(Map<String, PeriodInOut> iomap) {
		this.iomap = iomap;
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
		return "ProductInOutMap [pdtId=" + pdtId + ", pdtCode=" + pdtCode + ", pdtModel=" + pdtModel + ", startStorage="
				+ startStorage + ", iomap=" + iomap + ", mtlTotalIn=" + mtlTotalIn + ", mtlTotalOut=" + mtlTotalOut
				+ ", storage=" + storage + "]";
	}
	
}
