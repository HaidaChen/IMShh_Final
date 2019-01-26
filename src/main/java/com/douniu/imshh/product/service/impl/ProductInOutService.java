package com.douniu.imshh.product.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.douniu.imshh.busdata.product.dao.IProductDao;
import com.douniu.imshh.busdata.product.domain.Product;
import com.douniu.imshh.common.IDInjector;
import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.material.domain.MaterialFilter;
import com.douniu.imshh.material.domain.MaterialInOut;
import com.douniu.imshh.material.domain.MaterialInOutMap;
import com.douniu.imshh.product.dao.IProductInOutDao;
import com.douniu.imshh.product.domain.BillDetail;
import com.douniu.imshh.product.domain.PeriodInOut;
import com.douniu.imshh.product.domain.ProductBill;
import com.douniu.imshh.product.domain.ProductFilter;
import com.douniu.imshh.product.domain.ProductInOut;
import com.douniu.imshh.product.domain.ProductInOutMap;
import com.douniu.imshh.product.domain.ProductOutBill;
import com.douniu.imshh.product.service.IProductInOutService;
import com.douniu.imshh.utils.DateUtil;

public class ProductInOutService implements IProductInOutService{
	private IProductInOutDao dao;
	private IProductDao pdao;
	
	public void insert(ProductBill bill){
		boolean in = true;
		if (bill instanceof ProductOutBill){
			in = false;
		}
		List<ProductInOut> details = new ArrayList<>();
		for (BillDetail detail : bill.getDetails()){
			ProductInOut inout = new ProductInOut();
			inout.setBill(bill);
			inout.setGenDate(bill.getBillDate());
			inout.setBillPeriod(DateUtil.Date2String(bill.getBillDate(), "yyyyMM"));
			inout.setSummary(bill.getBillReason());
			
			inout.setProduct(detail.getProduct());
			if (in){
				inout.setInQuantity(detail.getQuantity());
			}else{
				inout.setOutQuantity(detail.getQuantity());
			}
			details.add(inout);
		}
		IDInjector.injector(details);
		dao.batchInsert(details);
	}
	
	public void update(ProductBill bill){
		delete(bill.getId());
		insert(bill);
	}
	
	public void delete(String billId){
		dao.deleteByBill(billId);
	}
	
	/**
	 * 列举所有原材料的出入明细，明细包含指定月份的期初库存、入库数量、出库数量、当前库存汇总四个指标
	 * */
	public PageResult getGlobalInOutPageResult(ProductFilter filter) {
		PageResult pr = new PageResult();
		List<ProductInOutMap> maps = new ArrayList<>();
		// 2、期初库存 = 对应开始单据期的上次盘点库存 + 上次盘点时间的入库数 - 上次盘点时间的出库数；
		List<ProductInOut> details = dao.getTotalInOut(filter);
		for (ProductInOut detail : details){
			ProductInOutMap map = null;
			if (!maps.contains(detail)){
				map = new ProductInOutMap(detail.getProduct());
				maps.add(map);
			}else {
				map = maps.get(maps.indexOf(detail));
			}
			map.getIomap().put(detail.getBillPeriod(), new PeriodInOut(detail.getBillPeriod(), detail.getInQuantity(), detail.getOutQuantity()));
		}
		
		pr.setRows(maps);
		pr.setTotal(dao.countTotalInOut(filter));
		return pr;
	}
	
	@Override
	public List<ProductInOutMap> queryGlobalInOut(ProductFilter filter) {
		List<ProductInOutMap> maps = new ArrayList<>();
		List<ProductInOut> details = dao.queryTotalInOut(filter);
		for (ProductInOut detail : details){
			ProductInOutMap map = null;
			if (!maps.contains(detail)){
				map = new ProductInOutMap(detail.getProduct());
				maps.add(map);
			}else {
				map = maps.get(maps.indexOf(detail));
			}
			map.getIomap().put(detail.getBillPeriod(), new PeriodInOut(detail.getBillPeriod(), detail.getInQuantity(), detail.getOutQuantity()));
		}
		return maps;
	}
	
	/**
	 * 列举单个原材料的出入明细，明细包含指定月份的出入流水和入库数量、入库金额、出库数量、出库金额汇总、当前库存汇总五个指标
	 */
	public List<ProductInOut> getInOutByProduct(String pdtId, String sPeriod, String ePeriod){
		List<ProductInOut> result = new ArrayList<>();
		
		//指定周期的出入明细记录
		Date startDate = DateUtil.string2Date(sPeriod + "-01");
		Date endDate = DateUtil.getLastDayOfYM(new Integer(ePeriod.substring(0, 4)), new Integer(ePeriod.substring(5)));
		List<ProductInOut> details = dao.getInOutByProduct(pdtId, startDate, endDate);
		result.addAll(details);
		
		//入库数合计
		int totalIn = dao.getTotalInQuantity(pdtId, startDate, endDate);
		ProductInOut ototalIn = new ProductInOut();
		ototalIn.setSummary("合计入库数量");
		ototalIn.setInQuantity(totalIn);
		result.add(ototalIn);
		
		//出库数合计
		int totalOut = dao.getTotalOutQuantity(pdtId, startDate, endDate);
		ProductInOut ototalOut = new ProductInOut();
		ototalOut.setSummary("合计出库数量");
		ototalOut.setOutQuantity(totalOut);
		result.add(ototalOut);
		
		//当前库存
		Product pdt = pdao.findById(pdtId);
		ProductInOut storage = new ProductInOut();
		storage.setInQuantity(pdt.getStorage());
		storage.setSummary("当前库存");
		result.add(storage);
		return result;
	}

	public void setDao(IProductInOutDao dao) {
		this.dao = dao;
	}

	public void setPdao(IProductDao pdao) {
		this.pdao = pdao;
	}

}
