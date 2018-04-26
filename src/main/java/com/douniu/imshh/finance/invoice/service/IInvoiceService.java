package com.douniu.imshh.finance.invoice.service;

import java.util.List;

import com.douniu.imshh.finance.invoice.domain.Invoice;

public interface IInvoiceService {
	List<Invoice> query(Invoice invoice);
	List<Invoice> queryNoPage(Invoice invoice);
	int count(Invoice invoice);
	Invoice getById(String id);
	void save(Invoice invoice);
	void delete(String id);
	void batchAdd(List<Invoice> invoices);
}
