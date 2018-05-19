package com.douniu.imshh.finance.order.domain;

import java.util.Date;

public class OrderAndDetail {
	private String identify; 
	private String custName;
	private Date orderDate;
	private Date deliveryTerm;
	private float exchangeRate;
	private float amountRMB;
	private float amountDollar;
	private String remark;
	private String pdtNo;
	private String pdtName;
	private String content;
	private int quantity;
	private float priceRMB;
	private float priceDollar;
	private float totlmentRMB;
	private float totlmentDollar;
	private String detailRemark;
	
	public OrderAndDetail(){super();}	
	
	public OrderAndDetail(String identify, String custName, Date orderDate, Date deliveryTerm, float exchangeRate, float amountRMB, float amountDollar, String remark, String pdtNo,
			String pdtName, String content, int quantity, float priceRMB, float priceDollar, float totlemntRMB, float totlemntDollar,
			String detailRemark) {
		super();
		this.identify = identify;
		this.custName = custName;
		this.orderDate = orderDate;
		this.deliveryTerm = deliveryTerm;
		this.exchangeRate = exchangeRate;
		this.amountRMB = amountRMB;
		this.amountDollar = amountDollar;
		this.remark = remark;
		this.pdtNo = pdtNo;
		this.pdtName = pdtName;
		this.content = content;
		this.quantity = quantity;
		this.priceRMB = priceRMB;
		this.priceDollar = priceDollar;
		this.totlmentRMB = totlemntRMB;
		this.totlmentDollar = totlemntDollar;
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

	public Date getDeliveryTerm() {
		return deliveryTerm;
	}

	public void setDeliveryTerm(Date deliveryTerm) {
		this.deliveryTerm = deliveryTerm;
	}

	public float getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(float exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public float getAmountRMB() {
		return amountRMB;
	}

	public void setAmountRMB(float amountRMB) {
		this.amountRMB = amountRMB;
	}

	public float getAmountDollar() {
		return amountDollar;
	}

	public void setAmountDollar(float amountDollar) {
		this.amountDollar = amountDollar;
	}

	public float getTotlmentRMB() {
		return totlmentRMB;
	}

	public void setTotlmentRMB(float totlmentRMB) {
		this.totlmentRMB = totlmentRMB;
	}

	public float getTotlmentDollar() {
		return totlmentDollar;
	}

	public void setTotlmentDollar(float totlmentDollar) {
		this.totlmentDollar = totlmentDollar;
	}

	
}
