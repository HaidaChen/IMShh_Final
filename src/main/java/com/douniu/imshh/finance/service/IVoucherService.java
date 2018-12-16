package com.douniu.imshh.finance.service;

import java.util.List;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.finance.domain.FinanceFilter;
import com.douniu.imshh.finance.domain.Voucher;

public interface IVoucherService {
	PageResult getPageResult(FinanceFilter filter);
	List<Voucher> query(FinanceFilter filter);
	Voucher getById(String id);
	boolean exist(Voucher voucher);
	int getNexNumber(Voucher voucher);
	
	void newVoucher(Voucher voucher);
	void updateVoucher(Voucher voucher);
	void deleteVoucher(String id);
}
