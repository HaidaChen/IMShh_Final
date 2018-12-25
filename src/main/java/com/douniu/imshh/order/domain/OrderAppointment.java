package com.douniu.imshh.order.domain;

import java.util.Date;

public class OrderAppointment {
	private String orderId;
	private Date deliveryTerm; /*交货日期*/
	private String shippingAddress; /*交货地点*/
	private int needInvoice;/*是否开票*/
	private String invoiceCategory;/*发票类型*/
	private String paymentMethod;/*支付方式*/
	private Float exchangeRate; /*约定汇率*/
	private String paymentAppointment;/*支付约定*/
	private String otherAppointment;/*其他约定*/
	
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getDeliveryTerm() {
		return deliveryTerm;
	}
	public void setDeliveryTerm(Date deliveryTerm) {
		this.deliveryTerm = deliveryTerm;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public int getNeedInvoice() {
		return needInvoice;
	}
	public void setNeedInvoice(int needInvoice) {
		this.needInvoice = needInvoice;
	}
	public String getInvoiceCategory() {
		return invoiceCategory;
	}
	public void setInvoiceCategory(String invoiceCategory) {
		this.invoiceCategory = invoiceCategory;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public Float getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(Float exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public String getPaymentAppointment() {
		return paymentAppointment;
	}
	public void setPaymentAppointment(String paymentAppointment) {
		this.paymentAppointment = paymentAppointment;
	}
	public String getOtherAppointment() {
		return otherAppointment;
	}
	public void setOtherAppointment(String otherAppointment) {
		this.otherAppointment = otherAppointment;
	}
	
	@Override
	public String toString() {
		return "OrderAppointment [orderId=" + orderId + ", deliveryTerm=" + deliveryTerm + ", shippingAddress="
				+ shippingAddress + ", needInvoice=" + needInvoice + ", invoiceCategory=" + invoiceCategory
				+ ", paymentMethod=" + paymentMethod + ", exchangeRate=" + exchangeRate + ", paymentAppointment="
				+ paymentAppointment + ", otherAppointment=" + otherAppointment + "]";
	}
	
}
