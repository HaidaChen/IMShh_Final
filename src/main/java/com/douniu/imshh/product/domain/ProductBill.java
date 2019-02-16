package com.douniu.imshh.product.domain;

import java.util.Date;
import java.util.List;

import com.douniu.imshh.order.domain.Order;

public class ProductBill {
	private String id;
	private String number;      /*单号*/
	private Order order;
	private Date billDate;      /*发生日期*/
	private String billReason;
	private String orderId;
	private List<BillDetail> details;  /*分录*/
	private int totalQuantity; /*合计数量*/
	
	private String preparedBy;   /*制单人*/	
	private String auditor;      /*审核人*/
	private String custodian;    /*保管*/
	
	private String remark;
	private int status = 1;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getBillReason() {
		return billReason;
	}
	public void setBillReason(String billReason) {
		this.billReason = billReason;
	}
	public List<BillDetail> getDetails() {
		return details;
	}
	public void setDetails(List<BillDetail> details) {
		this.details = details;
	}
	public int getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public String getPreparedBy() {
		return preparedBy;
	}
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public String getCustodian() {
		return custodian;
	}
	public void setCustodian(String custodian) {
		this.custodian = custodian;
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
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	@Override
	public String toString() {
		return "ProductBill [id=" + id + ", number=" + number + ", order=" + order + ", billDate=" + billDate
				+ ", billReason=" + billReason + ", orderId=" + orderId + ", details=" + details + ", totalQuantity="
				+ totalQuantity + ", preparedBy=" + preparedBy + ", auditor=" + auditor + ", custodian=" + custodian
				+ ", remark=" + remark + ", status=" + status + "]";
	}
	
}
