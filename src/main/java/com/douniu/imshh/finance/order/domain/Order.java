package com.douniu.imshh.finance.order.domain;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.douniu.imshh.common.BaseQO;

public class Order extends BaseQO {
	private String id;
	private String identify; /*订单编号*/
	private String custId;   /*客户ID*/
	private String custName; /*客户名称*/
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date orderDate;  /*订单生成日期*/
	private float amount;    /*订单总金额*/
	private int state = 1;   /*订单状态*/
	private List<OrderDetail> details;  /*订单明细*/
	
	private String remark;
	private int status = 1;
	
	public Order(){}
	
	public Order(String id, String identify, String custName, Date orderDate, float amount, String remark) {
		super();
		this.id = id;
		this.identify = identify;
		this.custName = custName;
		this.orderDate = orderDate;
		this.amount = amount;
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
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
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
	@Override
	public String toString() {
		return "Order [id=" + id + ", identify=" + identify + ", custId=" + custId + ", custName=" + custName
				+ ", orderDate=" + orderDate + ", amount=" + amount + ", state=" + state + ", details=" + details
				+ ", remark=" + remark + ", status=" + status + "]";
	}	
}
