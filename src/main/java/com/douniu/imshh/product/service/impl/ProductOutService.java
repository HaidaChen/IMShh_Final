package com.douniu.imshh.product.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.douniu.imshh.busdata.product.service.IProductService;
import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.product.dao.IProductOutDao;
import com.douniu.imshh.product.domain.BillDetail;
import com.douniu.imshh.product.domain.ProductFilter;
import com.douniu.imshh.product.domain.ProductOutBill;
import com.douniu.imshh.product.domain.ProductOutTableRow;
import com.douniu.imshh.product.service.IProductInOutService;
import com.douniu.imshh.product.service.IProductOutService;
import com.douniu.imshh.utils.LikeFlagUtil;

public class ProductOutService implements IProductOutService{
	private IProductOutDao dao;
	private IProductService pdtService;
	private IProductInOutService ioService;
	
	@Override
	public PageResult getPageResult(ProductFilter filter) {
		PageResult pr = new PageResult();
		ProductFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"number"});
		List<ProductOutBill> result = dao.getPageResult(condition);
		pr.setRows(change2TableRows(result));
		pr.setTotal(dao.count(condition));
		return pr;
	}

	@Override
	public ProductOutBill getById(String id) {
		return dao.getById(id);
	}

	@Override
	public void newProductOut(ProductOutBill productOut) {
		IDInjector.injector(productOut);
		List<BillDetail> details = productOut.getDetails();
		for (BillDetail detail : details){
			detail.setBillId(productOut.getId());
			pdtService.addStorage(detail.getProduct().getId(), 0-detail.getQuantity());
		}
		IDInjector.injector(details);
		dao.insertDetails(details);
		dao.insert(productOut);
		ioService.insert(productOut);
	}

	@Override
	public void updateProductOut(ProductOutBill productOut) {
		ProductOutBill o_productOut = dao.getById(productOut.getId());
		List<BillDetail> o_details = o_productOut.getDetails();
		for (BillDetail detail : o_details){
			pdtService.addStorage(detail.getProduct().getId(), detail.getQuantity());
		}
		
		dao.update(productOut);
		dao.deleteDetailsByBillId(productOut.getId());
		List<BillDetail> details = productOut.getDetails();
		for (BillDetail detail : details){
			detail.setBillId(productOut.getId());
			pdtService.addStorage(detail.getProduct().getId(), 0-detail.getQuantity());
		}
		IDInjector.injector(details);
		dao.insertDetails(productOut.getDetails());
		ioService.update(productOut);
	}

	@Override
	public void deleteProductOut(String id) {
		ProductOutBill productout = dao.getById(id);
		dao.delete(id);
		dao.deleteDetailsByBillId(id);
		List<BillDetail> details = productout.getDetails();
		for (BillDetail detail : details){
			pdtService.addStorage(detail.getProduct().getId(), 0+detail.getQuantity());
		}
		ioService.delete(id);
	}

	@Override
	public List<ProductOutTableRow> exportBill(ProductFilter filter) {
		ProductFilter condition = LikeFlagUtil.appendLikeFlag(filter, new String[]{"number"});
		return change2TableRows(dao.query(condition));
	}

	public void setDao(IProductOutDao dao) {
		this.dao = dao;
	}

	public void setPdtService(IProductService pdtService) {
		this.pdtService = pdtService;
	}
	
	public void setIoService(IProductInOutService ioService) {
		this.ioService = ioService;
	}

	private List<ProductOutTableRow> change2TableRows(List<ProductOutBill> bills){
		List<ProductOutTableRow> rows = new ArrayList<>();
		for (ProductOutBill bill : bills){
			List<BillDetail> details = bill.getDetails();
			for (BillDetail detail : details){
				ProductOutTableRow row = new ProductOutTableRow(bill, detail);
				rows.add(row);
			}
		}
		return rows;
	}
}
