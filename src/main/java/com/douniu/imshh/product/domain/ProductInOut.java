package com.douniu.imshh.product.domain;

import java.util.Date;

import com.douniu.imshh.busdata.product.domain.Product;

public class ProductInOut {
	private String id;	
	private Date genDate;
	private String billPeriod;
	private int inQuantity;
	private int outQuantity;
	private int balanceQuantity;
	private String summary;
	private ProductBill bill;
	private Product product;
			
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Date getGenDate() {
		return genDate;
	}


	public void setGenDate(Date genDate) {
		this.genDate = genDate;
	}


	public String getBillPeriod() {
		return billPeriod;
	}


	public void setBillPeriod(String billPeriod) {
		this.billPeriod = billPeriod;
	}


	public int getInQuantity() {
		return inQuantity;
	}


	public void setInQuantity(int inQuantity) {
		this.inQuantity = inQuantity;
	}


	public int getOutQuantity() {
		return outQuantity;
	}


	public void setOutQuantity(int outQuantity) {
		this.outQuantity = outQuantity;
	}


	public int getBalanceQuantity() {
		return balanceQuantity;
	}


	public void setBalanceQuantity(int balanceQuantity) {
		this.balanceQuantity = balanceQuantity;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public ProductBill getBill() {
		return bill;
	}


	public void setBill(ProductBill bill) {
		this.bill = bill;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}


	@Override
	public String toString() {
		return "ProductlInOut [id=" + id + ", genDate=" + genDate + ", billPeriod=" + billPeriod + ", inQuantity="
				+ inQuantity + ", outQuantity=" + outQuantity + ", balanceQuantity=" + balanceQuantity + ", summary="
				+ summary + ", bill=" + bill + ", product=" + product + "]";
	}


	@Override
	public boolean equals(Object target){
		if (target instanceof ProductInOut){
			ProductInOut _targe = (ProductInOut)target;
			return this.product.getId().equals(_targe.getProduct().getId());
		}
		
		if (target instanceof ProductInOutMap){
			ProductInOutMap _target = (ProductInOutMap)target;
			return this.product.getId().equals(_target.getPdtId());
		}
			
		
		return false;
	}

}
