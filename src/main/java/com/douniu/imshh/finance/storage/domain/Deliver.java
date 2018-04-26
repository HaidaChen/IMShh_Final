package com.douniu.imshh.finance.storage.domain;

import java.util.Date;

import com.douniu.imshh.common.BaseQO;

public class Deliver extends BaseQO{
	private String id;
	private Date deliverDate;
	private String deliverType;
	private String orderId;
	private String orderIdentify;
	private String pdtNo;	
	private String content;
	private int amount;	
	private String remark;
	
	private Date modifyDate;
	private int status = 1;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getDeliverDate() {
		return deliverDate;
	}
	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}
	public String getDeliverType() {
		return deliverType;
	}
	public void setDeliverType(String deliverType) {
		this.deliverType = deliverType;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderIdentify() {
		return orderIdentify;
	}
	public void setOrderIdentify(String orderIdentify) {
		this.orderIdentify = orderIdentify;
	}
	public String getPdtNo() {
		return pdtNo;
	}
	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Deliver [id=" + id + ", deliverDate=" + deliverDate + ", deliverType=" + deliverType + ", orderId="
				+ orderId + ", orderIdentify=" + orderIdentify + ", pdtNo=" + pdtNo + ", content=" + content
				+ ", amount=" + amount + ", remark=" + remark + ", modifyDate=" + modifyDate + ", status=" + status
				+ "]";
	}	
}
