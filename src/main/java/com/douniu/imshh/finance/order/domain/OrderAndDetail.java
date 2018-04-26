package com.douniu.imshh.finance.order.domain;

import java.util.Date;

public class OrderAndDetail {
	private String identify; 
	private String custName;
	private Date orderDate;
	private float amount;
	private String remark;
	private String pdtNo;
	private String pdtName;
	private String content;
	private int quantity;
	private float priceRMB;
	private float priceDollar;
	private float totlemnt;
	private float progress;
	private String detailRemark;
	
	public OrderAndDetail(){super();}	
	
	public OrderAndDetail(String identify, String custName, Date orderDate, float amount, String remark, String pdtNo,
			String pdtName, String content, int quantity, float priceRMB, float priceDollar, float totlemnt,
			float progress, String detailRemark) {
		super();
		this.identify = identify;
		this.custName = custName;
		this.orderDate = orderDate;
		this.amount = amount;
		this.remark = remark;
		this.pdtNo = pdtNo;
		this.pdtName = pdtName;
		this.content = content;
		this.quantity = quantity;
		this.priceRMB = priceRMB;
		this.priceDollar = priceDollar;
		this.totlemnt = totlemnt;
		this.progress = progress;
		this.detailRemark = detailRemark;
	}



	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
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
	public String getPdtNo() {
		return pdtNo;
	}
	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
	}
	public String getPdtName() {
		return pdtName;
	}
	public void setPdtName(String pdtName) {
		this.pdtName = pdtName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getPriceRMB() {
		return priceRMB;
	}
	public void setPriceRMB(float priceRMB) {
		this.priceRMB = priceRMB;
	}
	public float getPriceDollar() {
		return priceDollar;
	}
	public void setPriceDollar(float priceDollar) {
		this.priceDollar = priceDollar;
	}
	public float getTotlemnt() {
		return totlemnt;
	}
	public void setTotlemnt(float totlemnt) {
		this.totlemnt = totlemnt;
	}
	public float getProgress() {
		return progress;
	}
	public void setProgress(float progress) {
		this.progress = progress;
	}
	public String getDetailRemark() {
		return detailRemark;
	}
	public void setDetailRemark(String detailRemark) {
		this.detailRemark = detailRemark;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
