package com.douniu.imshh.product.domain;

public class PeriodInOut {
	private String period;
	private float inQuantity;
	private float outQuantity;
	
	public PeriodInOut() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PeriodInOut(String period, float inQuantity, float outQuantity) {
		super();
		this.period = period;
		this.inQuantity = inQuantity;
		this.outQuantity = outQuantity;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	
	public float getInQuantity() {
		return inQuantity;
	}
	public void setInQuantity(float inQuantity) {
		this.inQuantity = inQuantity;
	}
	public float getOutQuantity() {
		return outQuantity;
	}
	public void setOutQuantity(float outQuantity) {
		this.outQuantity = outQuantity;
	}
	
	
}
