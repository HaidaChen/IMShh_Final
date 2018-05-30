package com.douniu.imshh.finance.invoice.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.douniu.imshh.common.BaseQO;

public class Invoice extends BaseQO{
	private String id;
	private Date invoiceDate;               
	private String customerName;  
	private String orderIdentify;
	private float amountWithTax;            /*价税合计*/
	private float valueAddTax;              /*应交增值税*/
	private String valueAddTaxCal;           /*应交增值税计算公式*/
	private float exciseTax;                /*应交消费税*/
	private String exciseTaxCal;             /*应交消费税计算公式*/
	private float constructionTax;          /*城建税*/
	private String constructionTaxCal;       /*城建税计算公式*/
	private float educationFee;             /*教育费附加*/
	private String educationFeeCal;          /*教育费附加计算公式*/
	private float totalTax;                 /*税款合计*/
	private String totalTaxCal;              /*税款合计计算公式*/
	private float drawback;                 /*退税*/
	private String drawbackCal;              /*退税*/
	
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
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getOrderIdentify() {
		return orderIdentify;
	}
	public void setOrderIdentify(String orderIdentify) {
		this.orderIdentify = orderIdentify;
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
	
	
	
	public String getValueAddTaxCal() {
		return valueAddTaxCal;
	}
	public void setValueAddTaxCal(String valueAddTaxCal) {
		this.valueAddTaxCal = valueAddTaxCal;
	}
	public String getExciseTaxCal() {
		return exciseTaxCal;
	}
	public void setExciseTaxCal(String exciseTaxCal) {
		this.exciseTaxCal = exciseTaxCal;
	}
	public String getConstructionTaxCal() {
		return constructionTaxCal;
	}
	public void setConstructionTaxCal(String constructionTaxCal) {
		this.constructionTaxCal = constructionTaxCal;
	}
	public String getEducationFeeCal() {
		return educationFeeCal;
	}
	public void setEducationFeeCal(String educationFeeCal) {
		this.educationFeeCal = educationFeeCal;
	}
	public String getTotalTaxCal() {
		return totalTaxCal;
	}
	public void setTotalTaxCal(String totalTaxCal) {
		this.totalTaxCal = totalTaxCal;
	}
	public String getDrawbackCal() {
		return drawbackCal;
	}
	public void setDrawbackCal(String drawbackCal) {
		this.drawbackCal = drawbackCal;
	}
	
	@Override
	public String toString() {
		return "Invoice [id=" + id + ", invoiceDate=" + invoiceDate + ", customerName=" + customerName
				+ ", orderIdentify=" + orderIdentify + ", amountWithTax=" + amountWithTax + ", valueAddTax="
				+ valueAddTax + ", valueAddTaxCal=" + valueAddTaxCal + ", exciseTax=" + exciseTax + ", exciseTaxCal="
				+ exciseTaxCal + ", constructionTax=" + constructionTax + ", constructionTaxCal=" + constructionTaxCal
				+ ", educationFee=" + educationFee + ", educationFeeCal=" + educationFeeCal + ", totalTax=" + totalTax
				+ ", totalTaxCal=" + totalTaxCal + ", drawback=" + drawback + ", drawbackCal=" + drawbackCal
				+ ", remark=" + remark + ", status=" + status + "]";
	}
	private float decimalMoney(float src){
		BigDecimal b = new BigDecimal(src);  		
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();  
	}
}
