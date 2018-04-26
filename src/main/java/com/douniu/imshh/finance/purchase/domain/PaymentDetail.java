package com.douniu.imshh.finance.purchase.domain;

import java.util.Date;

import com.douniu.imshh.common.BaseQO;

public class PaymentDetail extends BaseQO{
	private String id;
	private String deliverDetailId;
	private Date paymentDate;
	private String accountId;
	private String accountNo;
	private float pamentAmount;
	private String tranUser; 
	private String tranBank;
	private String tranAccountNo;
	
	private String remark;
    private int status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPurchaseDetailId() {
		return deliverDetailId;
	}
	public void setPurchaseDetailId(String purchaseDetailId) {
		deliverDetailId = purchaseDetailId;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public float getPamentAmount() {
		return pamentAmount;
	}
	public void setPamentAmount(float pamentAmount) {
		this.pamentAmount = pamentAmount;
	}
	public String getTranUser() {
		return tranUser;
	}
	public void setTranUser(String tranUser) {
		this.tranUser = tranUser;
	}
	public String getTranBank() {
		return tranBank;
	}
	public void setTranBank(String tranBank) {
		this.tranBank = tranBank;
	}
	public String getTranAccountNo() {
		return tranAccountNo;
	}
	public void setTranAccountNo(String tranAccountNo) {
		this.tranAccountNo = tranAccountNo;
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
	
	public String getDeliverDetailId() {
		return deliverDetailId;
	}
	public void setDeliverDetailId(String deliverDetailId) {
		this.deliverDetailId = deliverDetailId;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	@Override
	public String toString() {
		return "PaymentDetail [id=" + id + ", deliverDetailId=" + deliverDetailId + ", paymentDate=" + paymentDate
				+ ", accountId=" + accountId + ", accountNo=" + accountNo + ", pamentAmount=" + pamentAmount
				+ ", tranUser=" + tranUser + ", tranBank=" + tranBank + ", tranAccountNo=" + tranAccountNo + ", remark="
				+ remark + ", status=" + status + "]";
	}
	 
}
