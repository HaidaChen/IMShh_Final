package com.douniu.imshh.finance.invoice.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.douniu.imshh.common.BaseQO;

public class Invoice extends BaseQO{
	private String id;
	private Date invoiceDate;               
	private String customerId;              
	private String customerName;            
	private float amountWithTax;            /*价税合计*/
	private float valueAddTax;              /*应交增值税*/
	private float exciseTax;                /*应交消费税*/
	private float constructionTax;          /*城建税*/
	private float educationFee;             /*教育费附加*/
	private float totalTax;                 /*税款合计*/
	private float drawback;                 /*退税*/
	
	private String remark;
	private int status = 1;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public float getAmountWithTax() {
		return amountWithTax;
	}
	public void setAmountWithTax(float amountWithTax) {		
		this.amountWithTax = decimalMoney(amountWithTax);
	}
	public float getValueAddTax() {
		return valueAddTax;
	}
	public void setValueAddTax(float valueAddTax) {
		this.valueAddTax = decimalMoney(valueAddTax);
	}
	public float getExciseTax() {
		return exciseTax;
	}
	public void setExciseTax(float exciseTax) {
		this.exciseTax = decimalMoney(exciseTax);
	}
	public float getConstructionTax() {
		return constructionTax;
	}
	public void setConstructionTax(float constructionTax) {
		this.constructionTax = decimalMoney(constructionTax);
	}
	public float getEducationFee() {
		return educationFee;
	}
	public void setEducationFee(float educationFee) {
		this.educationFee = decimalMoney(educationFee);
	}
	public float getTotalTax() {
		return totalTax;
	}
	public void setTotalTax(float totalTax) {
		this.totalTax = decimalMoney(educationFee);
	}
	public float getDrawback() {
		return drawback;
	}
	public void setDrawback(float drawback) {
		this.drawback = decimalMoney(drawback);
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
		return "Invoice [id=" + id + ", invoiceDate=" + invoiceDate + ", customerId=" + customerId + ", customerName="
				+ customerName + ", amountWithTax=" + amountWithTax + ", valueAddTax=" + valueAddTax + ", exciseTax="
				+ exciseTax + ", constructionTax=" + constructionTax + ", educationFee=" + educationFee + ", totalTax="
				+ totalTax + ", drawback=" + drawback + ", remark=" + remark + ", status=" + status + "]";
	}
	
	private float decimalMoney(float src){
		BigDecimal b = new BigDecimal(src);  
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();  
	}
}
