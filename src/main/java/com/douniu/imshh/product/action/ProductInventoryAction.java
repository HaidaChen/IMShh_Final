package com.douniu.imshh.product.action;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douniu.imshh.common.PageResult;
import com.douniu.imshh.product.domain.Inventory;
import com.douniu.imshh.product.domain.ProductFilter;
import com.douniu.imshh.product.service.IInventoryService;
import com.douniu.imshh.utils.DateUtil;
import com.douniu.imshh.utils.GsonUtil;

@Controller
@RequestMapping("/pdtinv")
public class ProductInventoryAction {
	@Autowired
	private IInventoryService service;
	
	@RequestMapping(value ="/loadSystemInventory", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String loadSystemInventory(ProductFilter filter){
		PageResult pr = service.getSystemInventory(filter);
		return GsonUtil.toJson(pr);
	}
	
	@RequestMapping(value="/inventory", produces = "application/json; charset=utf-8")
	@ResponseBody
	public void inventory(){
		Inventory inv = new Inventory();
		inv.setInventoryDate(new Date());
		service.inventory(inv);
	}
	
	@RequestMapping(value="/reset", produces = "application/json; charset=utf-8")
	@ResponseBody
	public void reset(){
		service.initCashInventory();
	}
	
	@RequestMapping(value="/saveInventoryItem", method=RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public void saveInventoryItem(String productId, float quantity){
		service.saveCacheItem(productId, quantity);
	}
	
	@RequestMapping(value ="/getHistroyInventory", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getHistroyInventory(ProductFilter filter){
		String sPeriod = filter.getStartPeriod();
		String ePeriod = filter.getEndPeriod();
		Date sDate = DateUtil.string2Date(sPeriod + "-01");
		Date eDate = DateUtil.getLastDayOfYM(new Integer(ePeriod.substring(0,4)), new Integer(ePeriod.substring(5)));
		filter.setStartDate(sDate);
		filter.setEndDate(eDate);
		List<Inventory> rs = service.queryInventory(filter);
		return GsonUtil.toJson(rs, "yyyy-MM-dd");
	}
	
	@RequestMapping(value ="/getHistroyInventoryDetail", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getHistroyInventoryDetail(ProductFilter filter){
		String sPeriod = filter.getStartPeriod();
		String ePeriod = filter.getEndPeriod();
		Date sDate = DateUtil.string2Date(sPeriod + "-01");
		Date eDate = DateUtil.getLastDayOfYM(new Integer(ePeriod.substring(0,4)), new Integer(ePeriod.substring(5)));
		filter.setStartDate(sDate);
		filter.setEndDate(eDate);
		PageResult rs = service.queryInventoryDetailPageResult(filter);
		return GsonUtil.toJson(rs, "yyyy-MM-dd");
	}
}
