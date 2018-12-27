package com.douniu.imshh.order.domain;

import com.douniu.imshh.common.BaseQO;

public class OrderFilter extends BaseQO {
	private String identify;
	private String orderType;
	private String billStatus;
	private String customerId;
	private String productId;
	
	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	@Override
	public String toString() {
		return "OrderFilter [identify=" + identify + ", orderType=" + orderType + ", billStatus=" + billStatus
				+ ", customerId=" + customerId + "]";
	}
}
