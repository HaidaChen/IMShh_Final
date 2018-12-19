package com.douniu.imshh.finance.service;

import java.util.List;

import com.douniu.imshh.finance.domain.Account;
import com.douniu.imshh.finance.domain.Voucher;

public interface IAccountService {
	void update(Voucher voucher);
	void insert(Voucher voucher);
	void delete(String voucherId);
	
	/**
	 * 要求返回的结果包含所有明细账发生金额和余额，并且结果集的第一条为期初余额，倒数第二条为期末合计，
	 * 倒数第一条为年度合计；
	 * 另外，会计科目是树形结构，那么多查询的是父科目下所有子科目的明细及其他三个指标的汇总
	 * @param subjectId
	 * @param period
	 * @return
	 */
	List<Account> getByPeriod(String subjectId, String period);
}
