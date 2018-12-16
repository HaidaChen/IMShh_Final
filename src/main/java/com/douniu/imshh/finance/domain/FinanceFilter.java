package com.douniu.imshh.finance.domain;

public class FinanceFilter {
	/*会计科目名称*/
	private String subName;
	/*会计科目编码*/
	private String subCode;
	/*会计科目分类*/
	private String subCategory;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "FinanceFilter [subName=" + subName + ", subCode=" + subCode + ", subCategory=" + subCategory
				+ ", remark=" + remark + "]";
	}
}
