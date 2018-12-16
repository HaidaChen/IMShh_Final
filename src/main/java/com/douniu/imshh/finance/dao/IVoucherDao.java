package com.douniu.imshh.finance.dao;

import java.util.List;

import com.douniu.imshh.finance.domain.FinanceFilter;
import com.douniu.imshh.finance.domain.Voucher;
import com.douniu.imshh.finance.domain.VoucherEntry;

public interface IVoucherDao {
	List<Voucher> getPageResult(FinanceFilter filter);
	int count(FinanceFilter filter);
	List<Voucher> query(FinanceFilter filter);
	Voucher getById(String id);
	Voucher getByName(Voucher voucher);
	int getMaxNumber(Voucher voucher);
	
	void insert(Voucher voucher);
	void update(Voucher voucher);
	void delete(String id);
	
	void insertEnteries(List<VoucherEntry> enteries);
	void deleteEnteriesByVoucher(String voucherId);
}
