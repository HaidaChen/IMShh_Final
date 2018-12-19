package com.douniu.imshh.finance.domain;

/**
 * 科目余额:由余额流水、期初余额、本期发生额、期末余额、本年发生额组成（1流水4指标）
 * 每次审核凭证通过后，根据凭证分录中的科目新增科目余额流水；
 * 期初余额 = 上期最后一条余额
 * 期末余额 = 本期最后一条余额
 * 本期发生 = 本期凭证汇总额
 * 本年发生 = 本年凭证汇总额
 * */
public class SubjectBalance {
	private String id;
	/*科目*/
	private Subject subject;
	/*余额*/
	private float balance;
	/*账期*/
	private String accountPeriod;
	/*凭证*/
	private Voucher voucher;
	/*凭证中已经包含了分录，这里定义该字段是因为凭证包含的是分录集合，而实际的余额应该是只对应一条分录*/
	private VoucherEntry voucherEntry;
	private String remark;
	private int status = 1;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public String getAccountPeriod() {
		return accountPeriod;
	}
	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}
	public Voucher getVoucher() {
		return voucher;
	}
	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}
	public VoucherEntry getVoucherEntry() {
		return voucherEntry;
	}
	public void setVoucherEntry(VoucherEntry voucherEntry) {
		this.voucherEntry = voucherEntry;
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
		return "SubjectBalance [id=" + id + ", subject=" + subject + ", balance=" + balance + ", accountPeriod="
				+ accountPeriod + ", voucher=" + voucher + ", voucherEntry=" + voucherEntry + ", remark=" + remark
				+ ", status=" + status + "]";
	}
	
}
