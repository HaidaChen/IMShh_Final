package com.douniu.imshh.finance.dashboard.domain;

import com.douniu.imshh.common.BaseQO;

public class MaterialStatistics extends BaseQO{
	private String year;
	/* 品名  */
	private String materialName;
	private String specification1;
	private String specification2;
	private String specification3;
	/* 采购数量 */
	private int orderQuantity;
	/* 采购金额 */
	private float orderAmount;
	/* 使用数量 */
	private int useQuantity;

    private int stock;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getSpecification1() {
		return specification1;
	}

	public void setSpecification1(String specification1) {
		this.specification1 = specification1;
	}

	public String getSpecification2() {
		return specification2;
	}

	public void setSpecification2(String specification2) {
		this.specification2 = specification2;
	}

	public String getSpecification3() {
		return specification3;
	}

	public void setSpecification3(String specification3) {
		this.specification3 = specification3;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public float getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(float orderAmount) {
		this.orderAmount = orderAmount;
	}

	public int getUseQuantity() {
		return useQuantity;
	}

	public void setUseQuantity(int useQuantity) {
		this.useQuantity = useQuantity;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "MaterialStatistics [year=" + year + ", materialName=" + materialName + ", specification1="
				+ specification1 + ", specification2=" + specification2 + ", specification3=" + specification3
				+ ", orderQuantity=" + orderQuantity + ", orderAmount=" + orderAmount + ", useQuantity=" + useQuantity
				+ ", stock=" + stock + "]";
	}	
    
    
}
