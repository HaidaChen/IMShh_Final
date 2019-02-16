package com.douniu.imshh.customer.domain;

import com.douniu.imshh.common.BaseQO;

public class CustomerFilter extends BaseQO{
	private String custName;
	private String custPhone;
	private String custContacts;
	private String remark;
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustPhone() {
		return custPhone;
	}
	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}
	public String getCustContacts() {
		return custContacts;
	}
	public void setCustContacts(String custContacts) {
		this.custContacts = custContacts;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "CustomerFilter [custName=" + custName + ", custPhone=" + custPhone + ", custContacts=" + custContacts
				+ ", remark=" + remark + "]";
	}
}
