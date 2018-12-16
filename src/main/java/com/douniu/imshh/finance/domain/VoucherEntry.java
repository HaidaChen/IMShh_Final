package com.douniu.imshh.finance.domain;

public class VoucherEntry {
	private String id;
	/*凭证ID*/
	private String voucherId;
	/*摘要*/
	private String summary;
	/*科目*/
	private Subject subject;
	/*借方金额*/
	private float debitAmount;
	/*贷方金额*/
	private float creditAmount;
	
	private String remark;
	private int status = 1;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVoucherId() {
		return voucherId;
	}
	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
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
		return "VoucherEntry [id=" + id + ", voucherId=" + voucherId + ", summary=" + summary + ", subject=" + subject
				+ ", debitAmount=" + debitAmount + ", creditAmount=" + creditAmount + ", remark=" + remark + ", status="
				+ status + "]";
	}
	
}
