package com.douniu.imshh.finance.service;

import com.douniu.imshh.finance.domain.VoucherEntry;

public interface ISubjectBalanceService {
	/**
	 * 余额发生变化：凭证新增、修改、删除导致余额发生变化；
	 * 如果是历史的凭证发生更新将会导致后续的余额都发生变化
	 * @param entry
	 */
	void change(VoucherEntry entry);
}
