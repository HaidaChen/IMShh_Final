package com.douniu.imshh.finance.domain;

import java.util.Date;

/**
 * 明细账簿：由凭证分录产生流水账，以及自动汇总的期初余额、本期合计和年度合计组成；
 * 其中，凭证分录流水账在凭证审核后自动产生
 * 期初余额（资产） = 上期期初余额 + 上期借方合计  - 上期贷方合计
 * 本期合计（资产） 由 借方合计、贷方合计、余额合计三个部分组成； 余额合计=借方合计 - 贷方合计
 * 年度合计（资产） 由 借方合计、贷方合计、余额合计三个部分组成；余额合计=借方合计 - 贷方合计
 * 
 * 至于期初余额、本期合计、年度合计这三个指标，有两种方案获得；方案一：持久化（在账期计算并保存到数据库）；
 * 方案二：非持久化（在查询时临时计算，计算值不保存到数据库）
 * 
 * 方案一，优点：效率高
 * 方案一，缺点：容错性差
 * 本质上这些汇总都是基于凭证的，如果凭证不能够及时的填写和审批的话就会出现汇总数据不完善，甚至级联更新的问题。
 * 如当前日期是2018年12月1日，假设系统在这时汇总以上三个指标，但是在8月30日发生了一笔账务，但是没有及时的
 * 填写凭证甚至等到12月2日才填写凭证，此时系统汇总时间已过，凭证将不会被汇总，数据出错。
 * 方案一，补救：允许一定时间（10天）的凭证漏填，在凭证审批后按照凭证日期逐月重算直至系统时间的当前月份
 * 
 * 方案二，优点：完全容错
 * 方案二，缺点：效率低，且随着系统使用年限增加一旦出现补填10年前的凭证，那么系统将发生10*12次的重新汇总。
 * 方案二，补救：允许一定时间的品质漏填
 * 
 * 经过以上对比，发现无论哪种方案关键在于允许多久时间的凭证漏填，另外，方案一代码复杂度相对于方案二代码复杂度稍低
 * （当然，如果允许漏填的时间越长方案一的代码复杂度不一定比方案二低，因为在允许漏填的情况下，方案一涉及到持久化数据
 * 的更新操作，如果允许漏填的时间越短，如小于1个月，那么方案一的代码复杂度相对较低）
 * 所以，综上所述，选择方案二，并且，约定允许漏填期限设置为3个月，可通过系统参数配置
 * @author Administrator
 *
 */
public class Account {
	private String id;
	/*科目*/
	private Subject subject;
	/*发生日期*/
	private Date accountDate;
	/*账期*/
	private String accountPeriod ;
	/*摘要，在凭证中包含了摘要，但是这里还是包含科目的目的是三个汇总指标不与凭证关联*/
	private String summary;
	/*凭证*/
	private Voucher voucher;
	/*借方金额，在凭证中包含了该属性，但是三个汇总指标不与凭证关联*/
	private float debitAmount;
	/*贷方金额，在凭证中包含了该属性，但是三个汇总指标不与凭证关联*/
	private float creditAmount;
	/*余额, 余额并不持久化到数据库中，是一个计算值*/
	private float balance;
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
	public String getAccountPeriod() {
		return accountPeriod;
	}
	public void setAccountPeriod(String accountPeriod) {
		this.accountPeriod = accountPeriod;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Voucher getVoucher() {
		return voucher;
	}
	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
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
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	
	public Date getAccountDate() {
		return accountDate;
	}
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}
	@Override
	public String toString() {
		return "SubsidiaryLedger [id=" + id + ", subject=" + subject + ", accountDate=" + accountDate
				+ ", accountPeriod=" + accountPeriod + ", summary=" + summary + ", voucher=" + voucher
				+ ", debitAmount=" + debitAmount + ", creditAmount=" + creditAmount + ", balance=" + balance + "]";
	}
}
