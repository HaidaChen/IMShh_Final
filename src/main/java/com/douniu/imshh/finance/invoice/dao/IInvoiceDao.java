package com.douniu.imshh.finance.invoice.dao;

import java.util.List;

import com.douniu.imshh.finance.invoice.domain.Invoice;

public interface IInvoiceDao {
	List<Invoice> query(Invoice invoice);
	List<Invoice> queryNoPage(Invoice invoice);
	int count(Invoice invoice);
	Invoice findById(String id);
	void insert(Invoice invoice);
	void batchInsert(List<Invoice> invoices);
	void update(Invoice invoice);
	void deleteById(String id);
}
