package com.douniu.imshh.finance.service.impl;

import java.util.List;

import org.springframework.util.StringUtils;

import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.finance.dao.IVoucherDao;
import com.douniu.imshh.finance.domain.FinanceFilter;
import com.douniu.imshh.finance.domain.Voucher;
import com.douniu.imshh.finance.domain.VoucherEntry;
import com.douniu.imshh.finance.service.IVoucherService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class VoucherService implements IVoucherService{
	private IVoucherDao dao;
	
	@Override
	public PageResult getPageResult(FinanceFilter filter) {
		PageResult pr = new PageResult();
		FinanceFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"preparedBy", "auditor", "voucherSummary", "subName", "remark"});
		if (!StringUtils.isEmpty(condition.getSubCode())){
			condition.setSubCode(condition.getSubCode() + "%");
		}
		pr.setRows(dao.getPageResult(condition));
		pr.setTotal(dao.count(condition));
		return pr;
	}

	@Override
	public List<Voucher> query(FinanceFilter filter) {
		FinanceFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"preparedBy", "auditor", "voucherSummary", "subName", "remark"});
		if (!StringUtils.isEmpty(condition.getSubCode())){
			condition.setSubCode(condition.getSubCode() + "%");
		}
		return dao.query(filter);
	}

	@Override
	public Voucher getById(String id) {
		return dao.getById(id);
	}

	@Override
	public boolean exist(Voucher voucher) {
		return dao.getByName(voucher) != null;
	}

	@Override
	public int getNexNumber(Voucher voucher) {
		return dao.getMaxNumber(voucher) + 1;
	}

	@Override
	public void newVoucher(Voucher voucher) {
		IDInjector.injector(voucher);
		List<VoucherEntry> entries = voucher.getEntries();
		for (VoucherEntry entry : entries){
			entry.setVoucherId(voucher.getId());
		}
		IDInjector.injector(entries);
		dao.insertEnteries(entries);
		dao.insert(voucher);
	}

	@Override
	public void updateVoucher(Voucher voucher) {
		dao.update(voucher);
		dao.deleteEnteriesByVoucher(voucher.getId());
		List<VoucherEntry> entries = voucher.getEntries();
		for (VoucherEntry entry : entries){
			entry.setVoucherId(voucher.getId());
		}
		IDInjector.injector(entries);
		dao.insertEnteries(entries);
	}

	@Override
	public void deleteVoucher(String id) {
		dao.delete(id);
		dao.deleteEnteriesByVoucher(id);
	}

	public void setDao(IVoucherDao dao) {
		this.dao = dao;
	}
}
