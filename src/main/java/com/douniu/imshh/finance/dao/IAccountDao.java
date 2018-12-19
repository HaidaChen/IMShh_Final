package com.douniu.imshh.finance.dao;

import java.util.Date;
import java.util.List;

import com.douniu.imshh.finance.domain.Account;

public interface IAccountDao {
	void batchInsert(List<Account> accounts);
	void deleteByVoucher(String voucherId);
	
	List<Account> getBySubjectCode(String subjectCode, Date startDate, Date endDate);
	float totalDebitBySubjectCode(String subjectCode, Date startDate, Date endDate);
	float totalCreditBySubjectCode(String subjectCode, Date startDate, Date endDate);
}
