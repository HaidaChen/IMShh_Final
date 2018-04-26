package com.douniu.imshh.finance.purchase.domain;

import java.util.Date;
import java.util.List;

import com.douniu.imshh.common.BaseQO;

public class PurchasePlan extends BaseQO{
	private String id;    
	private String identify;
	private String orderId;
	private String orderIdentify;     /*关联订单*/
	private Date createDate;          /*创建日期*/

	private float money;               /*金额*/
	private float paid;                 /*已支付*/
	private float balance;              /*余额*/
	
	private int planStatus = 1;           /*采购计划状态1：待收货，2：待付款，3：已完成*/
	
	private List<PurchaseDetail> details;
	
	private String remark;
	private int status = 1;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getPurchaseDate() {
		return createDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.createDate = purchaseDate;
	}
	
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public float getPaid() {
		return paid;
	}
	public void setPaid(float paid) {
		this.paid = paid;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}	
	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
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
	public List<PurchaseDetail> getDetails() {
		return details;
	}
	public void setDetails(List<PurchaseDetail> details) {
		this.details = details;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getPlanStatus() {
		return planStatus;
	}
	public void setPlanStatus(int planStatus) {
		this.planStatus = planStatus;
	}
	@Override
	public String toString() {
		return "PurchasePlan [id=" + id + ", identify=" + identify + ", orderId=" + orderId + ", orderIdentify="
				+ orderIdentify + ", createDate=" + createDate + ", amount=" + money + ", paid=" + paid + ", balance="
				+ balance + ", planStatus=" + planStatus + ", remark=" + remark + ", status=" + status + "]";
	}
		
}
