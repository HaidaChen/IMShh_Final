package com.douniu.imshh.product.domain;

public class InventoryDetail {
	private String id;
	private String inventoryId;
	private Product product;
	/*系统库存*/
	private int expectQuantity;
	/*盘点库存*/
	private int actualQuantity;
	/*盘盈盘亏*/
	private int profitLoss;
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

	public String getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getExpectQuantity() {
		return expectQuantity;
	}

	public void setExpectQuantity(int expectQuantity) {
		this.expectQuantity = expectQuantity;
	}

	public int getActualQuantity() {
		return actualQuantity;
	}

	public void setActualQuantity(int actualQuantity) {
		this.actualQuantity = actualQuantity;
	}

	public int getProfitLoss() {
		return profitLoss;
	}

	public void setProfitLoss(int profitLoss) {
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
		return "InventoryDetail [id=" + id + ", inventoryId=" + inventoryId + ", product=" + product
				+ ", expectQuantity=" + expectQuantity + ", actualQuantity=" + actualQuantity + ", profitLoss="
				+ profitLoss + ", price=" + price + ", amount=" + amount + ", status=" + status + "]";
	}

	@Override
	public boolean equals(Object target){
		if (target instanceof InventoryDetail){
			InventoryDetail _targe = (InventoryDetail)target;
			return this.product.getId().equals(_targe.getProduct().getId());
		}
		
		if (target instanceof InventoryMap){
			InventoryMap _target = (InventoryMap)target;
			return this.product.getId().equals(_target.getPdtId());
		}
			
		
		return false;
	}
}
