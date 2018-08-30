package com.douniu.imshh.finance.dashboard.domain;

import com.douniu.imshh.common.BaseQO;

public class ProductStatistics extends BaseQO{
	private String year;
	/* 货号  */
	private String pdtNo;
	/* 订购数量 */
	private int orderQuantity;
	/* 生产数量 */
	private int productionQuantity;
	/* 生产进度 */
	private float productionRatio;
	/* 交付数量 */
	private int deliverQuantity;
	/* 交付进度 */
	private float deliverRatio;
	
	private int stock;
	
	/* 订购金额 */
	private float orderAmount;
	/* 交付金额 */
	private float deliverAmount;
	
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getPdtNo() {
		return pdtNo;
	}
	public void setPdtNo(String pdtNo) {
		this.pdtNo = pdtNo;
	}
	public int getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public int getProductionQuantity() {
		return productionQuantity;
	}
	public void setProductionQuantity(int productionQuantity) {
		this.productionQuantity = productionQuantity;
	}
	public float getProductionRatio() {
		return productionRatio;
	}
	public void setProductionRatio(float productionRatio) {
		this.productionRatio = productionRatio;
	}
	public int getDeliverQuantity() {
		return deliverQuantity;
	}
	public void setDeliverQuantity(int deliverQuantity) {
		this.deliverQuantity = deliverQuantity;
	}
	public float getDeliverRatio() {
		return deliverRatio;
	}
	public void setDeliverRatio(float deliverRatio) {
		this.deliverRatio = deliverRatio;
	}
	public float getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(float orderAmount) {
		this.orderAmount = orderAmount;
	}
	public float getDeliverAmount() {
		return deliverAmount;
	}
	public void setDeliverAmount(float deliverAmount) {
		this.deliverAmount = deliverAmount;
	}	
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	@Override
	public String toString() {
		return "ProductStatistics [pdtNo=" + pdtNo + ", orderQuantity=" + orderQuantity + ", productionQuantity="
				+ productionQuantity + ", productionRatio=" + productionRatio + ", deliverQuantity=" + deliverQuantity
				+ ", deliverRatio=" + deliverRatio + ", orderAmount=" + orderAmount + ", deliverAmount=" + deliverAmount
				+ "]";
	}
	
}
