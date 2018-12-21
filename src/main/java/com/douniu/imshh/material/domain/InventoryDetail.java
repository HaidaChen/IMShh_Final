package com.douniu.imshh.material.domain;

public class InventoryDetail {
	private String id;
	private Material material;
	/*系统库存*/
	private float expectQuantity;
	/*盘点库存*/
	private float actualQuantity;
	/*盘盈盘亏*/
	private float profitLoss;
	/*盘点单价*/
	private float price;
	/*盘点金额*/
	private float amount;
	
	private int status = 1;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public float getExpectQuantity() {
		return expectQuantity;
	}

	public void setExpectQuantity(float expectQuantity) {
		this.expectQuantity = expectQuantity;
	}

	public float getActualQuantity() {
		return actualQuantity;
	}

	public void setActualQuantity(float actualQuantity) {
		this.actualQuantity = actualQuantity;
	}

	public float getProfitLoss() {
		return profitLoss;
	}

	public void setProfitLoss(float profitLoss) {
		this.profitLoss = profitLoss;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "InventoryDetail [id=" + id + ", material=" + material + ", expectQuantity=" + expectQuantity
				+ ", actualQuantity=" + actualQuantity + ", profitLoss=" + profitLoss + ", price=" + price + ", amount="
				+ amount + ", status=" + status + "]";
	}
	
}
