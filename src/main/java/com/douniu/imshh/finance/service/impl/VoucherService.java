package com.douniu.imshh.finance.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.finance.dao.IVoucherDao;
import com.douniu.imshh.finance.domain.FinanceFilter;
import com.douniu.imshh.finance.domain.Voucher;
import com.douniu.imshh.finance.domain.VoucherEntry;
import com.douniu.imshh.finance.domain.VoucherTableRow;
import com.douniu.imshh.finance.service.IAccountService;
import com.douniu.imshh.finance.service.IVoucherService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class VoucherService implements IVoucherService{
	private IVoucherDao dao;
	private IAccountService aService;
	
	@Override
	public PageResult getPageResult(FinanceFilter filter) {
		PageResult pr = new PageResult();
		FinanceFilter condition = processCondition(filter);
		pr.setRows(change2TableRows(dao.getPageResult(condition)));
		pr.setTotal(dao.count(condition));
		return pr;
	}

	@Override
	public List<Voucher> query(FinanceFilter filter) {
		FinanceFilter condition = processCondition(filter);
		return dao.query(condition);
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
		
		aService.insert(voucher);
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
		
		aService.update(voucher);
	}

	@Override
	public void deleteVoucher(String id) {
		dao.delete(id);
		dao.deleteEnteriesByVoucher(id);
		
		aService.delete(id);
	}

	public void setDao(IVoucherDao dao) {
		this.dao = dao;
	}	
	
	public void setaService(IAccountService aService) {
		this.aService = aService;
	}

	private List<VoucherTableRow> change2TableRows(List<Voucher> vouchers){
		List<VoucherTableRow> rows = new ArrayList<>();
		for (Voucher voucher : vouchers){
			List<VoucherEntry> entries = voucher.getEntries();
			for (VoucherEntry entry : entries){
				VoucherTableRow row = new VoucherTableRow(voucher, entry);
				rows.add(row);
			}
		}
		return rows;
	}
	
	private FinanceFilter processCondition(FinanceFilter filter){
		FinanceFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"preparedBy", "auditor", "voucherSummary", "subName", "remark"});
		if (!StringUtils.isEmpty(condition.getSubCode())){
			condition.setSubCode(condition.getSubCode() + "%");
		}
		if (!StringUtils.isEmpty(condition.getStartBillPeriod())){
			String sbillPeriod = condition.getStartBillPeriod() + "01";
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
			try {
				Date startDate = simpleDateFormat.parse(sbillPeriod);
				condition.setStartDate(startDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!StringUtils.isEmpty(condition.getEndBillPeriod())){
			String ebillPeriod = condition.getEndBillPeriod();
			int year = new Integer(ebillPeriod.substring(0, 4));
			int month = new Integer(ebillPeriod.substring(4)) - 1;
			Calendar c = Calendar.getInstance();
			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONTH, month);
			int lastDay = c.getActualMaximum(Calendar.DATE);
			c.set(Calendar.DAY_OF_MONTH, lastDay); 
			Date endDate = c.getTime();
			condition.setEndDate(endDate);
		}
		return condition;
	}
	
}
