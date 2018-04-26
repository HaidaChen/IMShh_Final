package com.douniu.imshh.finance.account.domain;

import com.douniu.imshh.common.BaseQO;

public class Account extends BaseQO{
	private String id;
	private String accountNo;
	private String bank;
	private String brachBank;
	private String bankLogo;
	private String accountUser;
	private String accountType;
	
	private int status = 1;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBrachBank() {
		return brachBank;
	}

	public void setBrachBank(String brachBank) {
		this.brachBank = brachBank;
	}

	public String getAccountUser() {
		return accountUser;
	}

	public void setAccountUser(String accountUser) {
		this.accountUser = accountUser;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBankLogo() {
		return bankLogo;
	}

	public void setBankLogo(String bankLogo) {
		this.bankLogo = bankLogo;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", accountNo=" + accountNo + ", bank=" + bank + ", brachBank=" + brachBank
				+ ", accountUser=" + accountUser + ", accountType=" + accountType + ", status=" + status + "]";
	}
	
}
