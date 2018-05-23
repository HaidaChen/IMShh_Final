package com.douniu.imshh.finance.order.domain;

import java.util.Date;
import java.util.List;

import com.douniu.imshh.common.BaseQO;

public class Order extends BaseQO {
	private String id;
	private String identify; /*订单编号*/
	private String custName; /*客户名称*/
	private Date orderDate;  /*订单生成日期*/
	private Date deliveryTerm; /*交货日期*/
	private Float exchangeRate; /*约定汇率*/
	private Float amountRMB;    /*订单总金额*/
	private Float amountDollar;    /*订单总金额*/
	private int state = 1;   /*订单状态*/
	private List<OrderDetail> details;  /*订单明细*/	
	
	private String remark;
	private int status = 1;
	
	public Order(){}
	
	public Order(String id, String identify, String custName, Date orderDate, Date deliveryTerm, Float exchangeRate, Float amountRMB, Float amountDollar, String remark) {
		super();
		this.id = id;
		this.identify = identify;
		this.custName = custName;
		this.orderDate = orderDate;
		this.deliveryTerm = deliveryTerm;
		this.exchangeRate = exchangeRate;
		this.amountRMB = amountRMB;	
		this.amountDollar = amountDollar;
		this.remark = remark;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
	public Float getAmountRMB() {
		return amountRMB;
	}

	public void setAmountRMB(Float amountRMB) {
		this.amountRMB = amountRMB;
	}

	public Float getAmountDollar() {
		return amountDollar;
	}

	public void setAmountDollar(Float amountDollar) {
		this.amountDollar = amountDollar;
	}

	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public List<OrderDetail> getDetails() {
		return details;
	}
	public void setDetails(List<OrderDetail> details) {
		this.details = details;
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
	
	public Date getDeliveryTerm() {
		return deliveryTerm;
	}

	public void setDeliveryTerm(Date deliveryTerm) {
		this.deliveryTerm = deliveryTerm;
	}

	public Float getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Float exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", identify=" + identify + ", custName=" + custName
				+ ", orderDate=" + orderDate + ", amountRMB=" + amountRMB + ", amountDollar=" + amountDollar
				+ ", state=" + state + ", details=" + details + ", deliveryTerm=" + deliveryTerm + ", exchangeRate="
				+ exchangeRate + ", remark=" + remark + ", status=" + status + "]";
	}
	
}
