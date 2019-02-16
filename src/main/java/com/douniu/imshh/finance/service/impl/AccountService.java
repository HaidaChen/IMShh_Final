package com.douniu.imshh.finance.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.finance.dao.ISubjectDao;
import com.douniu.imshh.finance.dao.IAccountDao;
import com.douniu.imshh.finance.domain.Subject;
import com.douniu.imshh.finance.domain.Account;
import com.douniu.imshh.finance.domain.Voucher;
import com.douniu.imshh.finance.domain.VoucherEntry;
import com.douniu.imshh.finance.service.IAccountService;
import com.douniu.imshh.utils.DateUtil;

public class AccountService implements IAccountService {
	private IAccountDao dao;
	private ISubjectDao sdao;
	
	@Override
	public void update(Voucher voucher) {
		delete(voucher.getId());
		insert(voucher);
	}

	@Override
	public void insert(Voucher voucher) {
		List<Account> accounts = new ArrayList<>();
		for (VoucherEntry entry : voucher.getEntries()){
			Account account = new Account();
			account.setAccountDate(voucher.getBillDate());
			account.setAccountPeriod(DateUtil.Date2String(voucher.getBillDate(), "yyyyMM"));
			account.setSubject(entry.getSubject());
			account.setSummary(entry.getSummary());
			account.setCreditAmount(entry.getCreditAmount());
			account.setDebitAmount(entry.getDebitAmount());
			account.setVoucher(voucher);
			accounts.add(account);
		}
		IDInjector.injector(accounts);
		dao.batchInsert(accounts);
	}

	@Override
	public void delete(String voucherId) {
		dao.deleteByVoucher(voucherId);
	}

	@Override
	public List<Account> getSubsidiaryLedger(String subId, String period) {
		Subject subject = sdao.getById(subId);
		return getAccountInfo(subject, period, true);
	}
	
	@Override
	public List<Account> getGeneralLedger(String period) {
		List<Account> result = new ArrayList<>();
		/*总账与明细账的区别在于只针对一级会计科目统计（往期余额、本期合计、本年累计）三个指标*/
		
		//查出所有总账科目
		List<Subject> subjects = sdao.getByParent("0");
		for (Subject subject : subjects){
			List<Account> subjectAccounts = getAccountInfo(subject, period, false);
			for (Account account : subjectAccounts){
				account.setSubject(subject);
			}
			result.addAll(subjectAccounts);
		}
		return result;
	}

	private List<Account> getAccountInfo(Subject subject, String period, boolean detail){
		List<Account> result = new ArrayList<>();
		//subject.setCode(subject.getCode()+"%");
		//第一条记录：往期余额
		Account pastRec = getPastTotalBalance(subject, period);
		result.add(pastRec);
		
		/*本期明细账记录，其中余额属性需要计算*/
		if (detail){
			Date sPeriod = DateUtil.string2Date(period + "01", "yyyyMMdd");
			Date ePeriod = DateUtil.getLastDayOfYM(new Integer(period.substring(0, 4)), new Integer(period.substring(4)));
			List<Account> details = dao.getBySubjectCode(subject.getCode() + "%", sPeriod, ePeriod);
			calculateBalance(pastRec.getBalance(), details, subject.getCategory());
			result.addAll(details);
		}
		
		//倒数第二条记录: 本期合计
		Account periodRec = getPeriodTotal(subject, period, pastRec);
		result.add(periodRec);
		
		/* 
		 * 倒数第一条记录:本年合计(本年借方金额合计、本年贷方金额合计、本年余额合计)
		 * 本年余额合计（资产类）= 往期（本年第一期）余额 + 本期借方金额合计 - 本期贷方金额合计
		 * 本年余额合计（负债类）= 往期（本年第一期）余额 + 本期贷方金额合计 - 本期借方金额合计
		 */
		String year = period.substring(0, 4);
		Date sDate = DateUtil.string2Date(year+"01"+"01", "yyyyMMdd");
		Date eDate = DateUtil.string2Date(year+"12"+"31", "yyyyMMdd");
		Account yearPastRec = getPastTotalBalance(subject, year+"01");
		float yearTotalDebitAmount = dao.totalDebitBySubjectCode(subject.getCode() + "%", sDate, eDate);
		float yearTotalCreditAmount = dao.totalCreditBySubjectCode(subject.getCode() + "%", sDate, eDate);
		float yearBalance = calculateBalance(yearPastRec.getBalance(), yearTotalDebitAmount, yearTotalCreditAmount, subject.getCategory());
		Account yearRec = new Account();
		yearRec.setSummary("本年累计");
		yearRec.setDebitAmount(yearTotalDebitAmount);
		yearRec.setCreditAmount(yearTotalCreditAmount);
		yearRec.setBalance(yearBalance);
		result.add(yearRec);
		
		return result;
	}
	
	/* 
	 * 往期余额：
	 * 往期余额（资产类） = 科目初始余额 + 往期借方金额合计 - 往期贷方金额合计
     * 往期余额（负债类） = 科目初始余额 + 往期贷方金额合计  - 往期借方金额合计
	 */
	private Account getPastTotalBalance(Subject subject, String period){
		String ctg = subject.getCategory();
		float initBalance = subject.getInitBalance();
		
		Date until = DateUtil.preDay(period + "01", "yyyyMMdd");
		float pastTotalDebitAmount = dao.totalDebitBySubjectCode(subject.getCode() + "%", null, until);
		float pastTotalCreditAmount = dao.totalCreditBySubjectCode(subject.getCode() + "%", null, until);
		float pastBalance = calculateBalance(initBalance, pastTotalDebitAmount, pastTotalCreditAmount, ctg);
		
		Account pastRec = new Account();
		pastRec.setSummary("期初余额");
		pastRec.setBalance(pastBalance);
		return pastRec;
	}
	
	/* 
	 * 本期合计(本期借方金额合计、本期贷方金额合计、本期余额合计)
	 * 本期余额合计（资产类）= 往期余额 + 本期借方金额合计 - 本期贷方金额合计
	 * 本期余额合计（负债类）= 往期余额 + 本期贷方金额合计 - 本期借方金额合计
	 */
	private Account getPeriodTotal(Subject subject, String period, Account pastAccount){
		String ctg = subject.getCategory();
		Date sDate = DateUtil.string2Date(period + "01", "yyyyMMdd");
		Date eDate = DateUtil.getLastDayOfYM(new Integer(period.substring(0, 4)), new Integer(period.substring(4)));
		float periodTotalDebitAmount = dao.totalDebitBySubjectCode(subject.getCode() + "%", sDate, eDate);
		float periodTotalCreditAmount = dao.totalCreditBySubjectCode(subject.getCode() + "%", sDate, eDate);
		float periodBalance = calculateBalance(pastAccount.getBalance(), periodTotalDebitAmount, periodTotalCreditAmount, ctg);
		
		Account periodRec = new Account();
		periodRec.setSummary("本期合计");
		periodRec.setDebitAmount(periodTotalDebitAmount);
		periodRec.setCreditAmount(periodTotalCreditAmount);
		periodRec.setBalance(periodBalance);
		return periodRec;
	}
	
	/* 
	 * 计算余额：
	 * 余额（资产类） = 期初余额 + 借方金额合计 - 贷方金额合计
     * 余额（负债类） = 期初余额 + 贷方金额合计  - 借方金额合计
	 */
	private float calculateBalance(float pastBalance, float debitAmount, float creditAmount, String directionLending){
		float balance = 0;
		if (directionLending.equals("01") ||
				directionLending.equals("05")){
			balance = pastBalance + debitAmount - creditAmount;
		}
		
		if (directionLending.equals("02") ||
				directionLending.equals("04") ||
				directionLending.equals("06")){
			balance = pastBalance + creditAmount - debitAmount;
		}
		return balance;
	}
	
	private void calculateBalance(float pastBalance, List<Account> accounts, String directionLending){
		if (accounts.size() == 0)
			return;
		float perBalance = pastBalance;
		for (Account account : accounts){
			perBalance = calculateBalance(perBalance, account.getDebitAmount(), account.getCreditAmount(), directionLending);
			account.setBalance(perBalance);
		}
	}

	public void setDao(IAccountDao dao) {
		this.dao = dao;
	}

	public void setSdao(ISubjectDao sdao) {
		this.sdao = sdao;
	}
	
}
