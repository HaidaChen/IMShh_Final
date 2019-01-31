package com.douniu.imshh.supplier.domain;

import com.douniu.imshh.common.BaseQO;

public class SupplierFilter extends BaseQO{
	private String suppName;
	private String suppPhone;
	private String suppContacts;
	private String remark;
	
	public String getSuppName() {
		return suppName;
	}
	public void setSuppName(String suppName) {
		this.suppName = suppName;
	}
	public String getSuppPhone() {
		return suppPhone;
	}
	public void setSuppPhone(String suppPhone) {
		this.suppPhone = suppPhone;
	}
	public String getSuppContacts() {
		return suppContacts;
	}
	public void setSuppContacts(String suppContacts) {
		this.suppContacts = suppContacts;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "SupplierFilter [suppName=" + suppName + ", suppPhone=" + suppPhone + ", suppContacts=" + suppContacts
				+ ", remark=" + remark + "]";
	}
}
