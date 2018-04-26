package com.douniu.imshh.finance.invoice.service.impl;

import java.util.List;

import com.douniu.imshh.finance.invoice.dao.IInvoiceDao;
import com.douniu.imshh.finance.invoice.domain.Invoice;
import com.douniu.imshh.finance.invoice.service.IInvoiceService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class InvoiceService implements IInvoiceService{
	private IInvoiceDao dao;
	
	@Override
	public List<Invoice> query(Invoice invoice) {
		// TODO Auto-generated method stub
		Invoice condition = LikeFlagUtil.appendLikeFlag(invoice, new String[]{"customerName"});
		return dao.query(condition);
	}

	@Override
	public List<Invoice> queryNoPage(Invoice invoice) {
		// TODO Auto-generated method stub
		Invoice condition = LikeFlagUtil.appendLikeFlag(invoice, new String[]{"customerName"});
		return dao.queryNoPage(condition);
	}

	@Override
	public int count(Invoice invoice) {
		// TODO Auto-generated method stub
		Invoice condition = LikeFlagUtil.appendLikeFlag(invoice, new String[]{"customerName"});
		return dao.count(condition);
	}

	@Override
	public Invoice getById(String id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	public void save(Invoice invoice) {
		// TODO Auto-generated method stub
		if (invoice.getId().equals("")){
			invoice.setId(System.currentTimeMillis()+"");
			invoice.setStatus(1);
			dao.insert(invoice);
		}else{
			dao.update(invoice);
		}
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		dao.deleteById(id);
	}

	@Override
	public void batchAdd(List<Invoice> invoices) {
		// TODO Auto-generated method stub
		dao.batchInsert(invoices);
	}

	public void setDao(IInvoiceDao dao) {
		this.dao = dao;
	}
	
	

}
