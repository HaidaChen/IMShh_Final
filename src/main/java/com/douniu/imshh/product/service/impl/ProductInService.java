package com.douniu.imshh.product.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.douniu.imshh.busdata.product.service.IProductService;
import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.product.dao.IProductInDao;
import com.douniu.imshh.product.domain.BillDetail;
import com.douniu.imshh.product.domain.ProductFilter;
import com.douniu.imshh.product.domain.ProductInBill;
import com.douniu.imshh.product.domain.ProductInTableRow;
import com.douniu.imshh.product.service.IProductInOutService;
import com.douniu.imshh.product.service.IProductInService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class ProductInService implements IProductInService{
	private IProductInDao dao;
	private IProductService pdtService;
	private IProductInOutService ioService;
	
	@Override
	public PageResult getPageResult(ProductFilter filter) {
		PageResult pr = new PageResult();
		ProductFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"number"});
		List<ProductInBill> result = dao.getPageResult(condition);
		pr.setRows(change2TableRows(result));
		pr.setTotal(dao.count(condition));
		return pr;
	}

	@Override
	public ProductInBill getById(String id) {
		return dao.getById(id);
	}

	@Override
	public void newProductIn(ProductInBill productIn) {
		IDInjector.injector(productIn);
		List<BillDetail> details = productIn.getDetails();
		for (BillDetail detail : details){
			detail.setBillId(productIn.getId());
			pdtService.addStorage(detail.getProduct().getId(), detail.getQuantity());
		}
		IDInjector.injector(details);
		dao.insertDetails(details);
		dao.insert(productIn);
		ioService.insert(productIn);
	}

	@Override
	public void updateProductIn(ProductInBill productIn) {
		ProductInBill o_productIn = dao.getById(productIn.getId());
		List<BillDetail> o_details = o_productIn.getDetails();
		for (BillDetail detail : o_details){
			pdtService.addStorage(detail.getProduct().getId(), 0-detail.getQuantity());
		}
		
		dao.update(productIn);
		dao.deleteDetailsByBillId(productIn.getId());
		List<BillDetail> details = productIn.getDetails();
		for (BillDetail detail : details){
			detail.setBillId(productIn.getId());
			pdtService.addStorage(detail.getProduct().getId(), detail.getQuantity());
		}
		IDInjector.injector(details);
		dao.insertDetails(productIn.getDetails());
		ioService.update(productIn);
	}

	@Override
	public void deleteProductIn(String id) {
		ProductInBill productIn = dao.getById(id);
		dao.delete(id);
		dao.deleteDetailsByBillId(id);
		List<BillDetail> details = productIn.getDetails();
		for (BillDetail detail : details){
			pdtService.addStorage(detail.getProduct().getId(), 0-detail.getQuantity());
		}
		ioService.delete(id);
	}

	@Override
	public List<ProductInTableRow> exportBill(ProductFilter filter) {
		ProductFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"number"});
		return change2TableRows(dao.query(condition));
	}

	public void setDao(IProductInDao dao) {
		this.dao = dao;
	}

	public void setPdtService(IProductService pdtService) {
		this.pdtService = pdtService;
	}
	
	
	public void setIoService(IProductInOutService ioService) {
		this.ioService = ioService;
	}

	private List<ProductInTableRow> change2TableRows(List<ProductInBill> bills){
		List<ProductInTableRow> rows = new ArrayList<>();
		for (ProductInBill bill : bills){
			List<BillDetail> details = bill.getDetails();
			for (BillDetail detail : details){
				ProductInTableRow row = new ProductInTableRow(bill, detail);
				rows.add(row);
			}
		}
		return rows;
	}
}
