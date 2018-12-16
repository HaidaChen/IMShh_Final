package com.douniu.imshh.finance.domain;

public class FinanceFilter {
	/*会计科目名称*/
	private String subName;
	/*会计科目编码*/
	private String subCode;
	/*会计科目分类*/
	private String subCategory;
	/*凭证字*/
	private int voucherWord;
	/*凭证号（起）*/
	private int startVouchernumber;
	/*凭证号（始）*/
	private int endVouchernumber;
	/*单据状态*/
	private int billStatus;
	/*制单人*/
	private String preparedBy;
	/*审核人*/
	private String auditor;
	/*凭证分录摘要*/
	private String voucherSummary;
	private String remark;
	
	public String getSubName() {
		return subName;
	}
	public void setSubName(String subName) {
		this.subName = subName;
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public int getVoucherWord() {
		return voucherWord;
	}
	public void setVoucherWord(int voucherWord) {
		this.voucherWord = voucherWord;
	}
	public int getStartVouchernumber() {
		return startVouchernumber;
	}
	public void setStartVouchernumber(int startVouchernumber) {
		this.startVouchernumber = startVouchernumber;
	}
	public int getEndVouchernumber() {
		return endVouchernumber;
	}
	public void setEndVouchernumber(int endVouchernumber) {
		this.endVouchernumber = endVouchernumber;
	}
	public int getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(int billStatus) {
		this.billStatus = billStatus;
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
	public String getVoucherSummary() {
		return voucherSummary;
	}
	public void setVoucherSummary(String voucherSummary) {
		this.voucherSummary = voucherSummary;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "FinanceFilter [subName=" + subName + ", subCode=" + subCode + ", subCategory=" + subCategory
				+ ", voucherWord=" + voucherWord + ", startVouchernumber=" + startVouchernumber + ", endVouchernumber="
				+ endVouchernumber + ", billStatus=" + billStatus + ", preparedBy=" + preparedBy + ", auditor="
				+ auditor + ", voucherSummary=" + voucherSummary + ", remark=" + remark + "]";
	}
}
