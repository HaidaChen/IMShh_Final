package com.douniu.imshh.finance.domain;

import java.util.Date;

public class VoucherTableRow {
	private String id;
	private Date billDate;
	/*凭证字: 1：记； 2：付；3：收；4：转*/
	private int word;
	/*凭证号*/
	private int number;
	/*制单人*/
	private String preparedBy;
	/*审核人*/
	private String auditor;
	/*凭证状态： 0：未审核，1：已审核*/
	private int billStatus;
	/*摘要*/
	private String summary;
	/*科目*/
	private Subject subject;
	/*借方金额*/
	private float debitAmount;
	/*贷方金额*/
	private float creditAmount;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public int getWord() {
		return word;
	}
	public void setWord(int word) {
		this.word = word;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getPreparedBy() {
		return preparedBy;
	}
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public int getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(int billStatus) {
		this.billStatus = billStatus;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public float getDebitAmount() {
		return debitAmount;
	}
	public void setDebitAmount(float debitAmount) {
		this.debitAmount = debitAmount;
	}
	public float getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(float creditAmount) {
		this.creditAmount = creditAmount;
	}
	
	@Override
	public String toString() {
		return "VoucherTableRow [id=" + id + ", billDate=" + billDate + ", word=" + word + ", number=" + number
				+ ", preparedBy=" + preparedBy + ", auditor=" + auditor + ", billStatus=" + billStatus + ", summary="
				+ summary + ", subject=" + subject + ", debitAmount=" + debitAmount + ", creditAmount=" + creditAmount
				+ "]";
	}
	
	public VoucherTableRow(){}
	
	public VoucherTableRow(Voucher voucher, VoucherEntry entry){
		this.id = voucher.getId();
		this.billDate = voucher.getBillDate();
		this.word = voucher.getWord();
		this.number = voucher.getNumber();
		this.preparedBy = voucher.getPreparedBy();
		this.auditor = voucher.getAttachments();
		this.billStatus = voucher.getBillStatus();
		this.summary = entry.getSummary();
		this.subject = entry.getSubject();
		this.debitAmount = entry.getDebitAmount();
		this.creditAmount = entry.getCreditAmount();
	}
}
